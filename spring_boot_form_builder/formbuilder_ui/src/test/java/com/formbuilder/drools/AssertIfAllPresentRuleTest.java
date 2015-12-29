package com.formbuilder.drools;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import lombok.val;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.formbuilder.rule.Evaluator;
import com.formbuilder.rule.Rule;

public class AssertIfAllPresentRuleTest {
	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;
	private static Logger logger = Logger.getLogger(AssertIfAllPresentRuleTest.class);
	String inputs;
	Rule ruleObj;
	String ruleString = "assertIfAllPresent";

	@Before
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String ruleFormat = "import java.util.Map import java.util.List import com.formbuilder.rule.Evaluator rule \"Values present\" when $rule: Evaluator(!this.eval()) $list: List() then $list.add(\"One of %s cannot be empty\"); end";
		String className = Character.toString(ruleString.charAt(0)).toUpperCase() + ruleString.substring(1);

		ruleObj = (Rule) Class.forName("com.formbuilder.rule." + className + "Rule").newInstance();
		ruleObj.setDroolString(ruleFormat);
		inputs = "name, project_type, start_date, end_date";
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
		// Get Drools for the same rules

	}

	@Test
	public void shouldFireAssertIfAllPresent() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("name", "myproject");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "1-1-16");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		Evaluator eval = (Evaluator) ruleObj;
		kSession.insert(eval);
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 0);
	}

	@Test
	public void shouldFireAssertIfAllPresentFailedWithNull() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("name", "myproject");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		Evaluator eval = (Evaluator) ruleObj;
		kSession.insert(eval);
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 1);
	}

	@Test
	public void shouldFireAssertIfAllPresentFailedWithEmpty() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("name", "myproject");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		Evaluator eval = (Evaluator) ruleObj;
		kSession.insert(eval);
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 1);
	}

}