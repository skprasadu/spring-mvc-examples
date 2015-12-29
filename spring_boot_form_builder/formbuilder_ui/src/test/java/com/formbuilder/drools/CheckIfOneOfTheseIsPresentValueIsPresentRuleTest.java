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

import com.formbuilder.rule.Rule;

public class CheckIfOneOfTheseIsPresentValueIsPresentRuleTest {
	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;
	private static Logger logger = Logger.getLogger(CheckIfOneOfTheseIsPresentValueIsPresentRuleTest.class);
	String inputs;
	Rule ruleObj;
	String ruleString = "checkIfOneOfTheseIsPresentValueIsPresent";

	@Before
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String ruleFormat = "import java.util.Map import java.util.List rule \"Values present\" when $map: Map(%s) $list: List() then if(%s) return; $list.add(\"%s\"); end";
		String className = Character.toString(ruleString.charAt(0)).toUpperCase() + ruleString.substring(1);

		ruleObj = (Rule) Class.forName("com.formbuilder.rule." + className + "Rule").newInstance();
		ruleObj.setDroolString(ruleFormat);
		inputs = "{\"when\": \"country_id != 0\", \"then\": \"citizenship_date != empty\"}";
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
	}

	@Test
	public void shouldFireAssertIfAllPresent() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("country_id", "1");
		mapa.put("citizenship_date", "1-1-15");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		kSession.insert(ruleObj.getData());
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 0);
	}

	@Test
	public void shouldFireAssertIfNotPresent() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("country_id", "0");
		mapa.put("citizenship_date", "");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		kSession.insert(ruleObj.getData());
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 0);
	}

	@Test
	public void shouldFireAssertIfPresentFailed() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("country_id", "1");
		mapa.put("citizenship_date", "");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		kSession.insert(ruleObj.getData());
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 1);
	}

	@Test
	public void shouldFireAssertIfPresent1() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		mapa.put("citizenship_date", "");
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		kSession.insert(ruleObj.getData());
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 0);
	}

	@Test
	public void shouldFireAssertIfPresent2() {
		// Get Rule infomration for a Given Form

		val mapa = new LinkedHashMap<String, String>();
		ruleObj.setData(mapa);

		kSession = kbase.newStatefulKnowledgeSession();

		List<String> list = new ArrayList<String>();
		kSession.insert(ruleObj.getData());
		kSession.insert(list);

		int actualNumberOfRulesFired = kSession.fireAllRules();

		assertEquals(list.size(), 0);
	}
}