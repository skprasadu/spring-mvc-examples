package com.formbuilder;

import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.UiRule;
import com.formbuilder.rule.Evaluator;
import com.formbuilder.rule.Rule;

@Service
public class UiRuleValidatorServiceImpl {
	private final UiRuleRepository repository;
	private final FormInformationRepository formInformationRepository;
	
	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;

	@Autowired
	public UiRuleValidatorServiceImpl(UiRuleRepository repository, FormInformationRepository formInformationRepository) {
		this.repository = repository;
		this.formInformationRepository = formInformationRepository;
	}	

	public void save(UiRule rule) {
		repository.save(rule);
	}

	public List<String> evaluate(String appName, String formId, LinkedHashMap<String, String> mapa) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		//This is the Drools rules part.
		//We also need to get the input
		FormInformation info =  formInformationRepository.findTemplateByName(appName, formId);
		//Get Input information for the rule
		String inputs = info.getRuleDetails().get(0).getValue();
		//Get Drools for the same rules
		UiRule uiRule =  repository.findRule(info.getRuleDetails().get(0).getName());
		assertNotNull(uiRule);
		String ruleFormat = uiRule.getRule();
		String className = Character.toString(info.getRuleDetails().get(0).getName().charAt(0)).toUpperCase() + info.getRuleDetails().get(0).getName().substring(1);

		Rule ruleObj = (Rule) Class.forName("com.formbuilder.rule." + className + "Rule").newInstance();
		ruleObj.setDroolString(ruleFormat);
		ruleObj.setNameListToValidate(inputs);
		
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
		
		if(ruleObj instanceof Evaluator){
			kSession.insert(ruleObj);
		} else{
			kSession.insert(ruleObj.getData());
		}
			
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();

		return list;
	}
}
