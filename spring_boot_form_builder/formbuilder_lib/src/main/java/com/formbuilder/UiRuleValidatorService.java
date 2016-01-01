package com.formbuilder;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formbuilder.dto.NameValue;
import com.formbuilder.dto.UiRule;
import com.formbuilder.rule.Evaluator;
import com.formbuilder.rule.Rule;

public abstract class UiRuleValidatorService {

	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;

	public UiRuleValidatorService() {
		super();
	}

	public abstract void save(UiRule rule);

	public List<String> evaluate(String appName, String formId, JSONObject mapa) {
		try {
			ObjectMapper om = new ObjectMapper();
			LinkedHashMap result = om.readValue(mapa.toJSONString(), LinkedHashMap.class);
			return evaluate(appName, formId, result);
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected abstract NameValue findTemplateByName(String appName, String formId);

	protected abstract UiRule findRule(String name);

	public List<String> evaluate(String appName, String formId, LinkedHashMap<String, String> mapa) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, ParseException {
		// This is the Drools rules part.
		// We also need to get the input
		NameValue info = findTemplateByName(appName, formId);
		// Get Input information for the rule
		String inputs = info.getValue();
		// Get Drools for the same rules
		UiRule uiRule = findRule(info.getName());
		assertNotNull(uiRule);
		String ruleFormat = uiRule.getRule();
		String className = Character.toString(info.getName().charAt(0)).toUpperCase() + info.getName().substring(1);

		Rule ruleObj = (Rule) Class.forName("com.formbuilder.rule." + className + "Rule").newInstance();
		ruleObj.setDroolString(ruleFormat);
		ruleObj.setNameListToValidate(inputs);
		ruleObj.setData(mapa);

		Resource myResource = ResourceFactory.newReaderResource(new StringReader(ruleObj.computeDroolsRule()));
		kbuilder.add(myResource, ResourceType.DRL);

		// Check the builder for errors
		if (kbuilder.hasErrors()) {
			System.out.println("******" + kbuilder.getErrors().toString());
			throw new RuntimeException("Unable to compile drl\".");
		}

		// get the compiled packages (which are serializable)
		pkgs = kbuilder.getKnowledgePackages();

		// add the packages to a knowledgebase (deploy the knowledge packages).
		kbase.addKnowledgePackages(pkgs);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();

		if (ruleObj instanceof Evaluator) {
			kSession.insert(ruleObj);
		} else {
			kSession.insert(ruleObj.getData());
		}

		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		return list;
	}
}