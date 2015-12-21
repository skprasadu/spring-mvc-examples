package com.formbuilder.drools;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.compiler.compiler.DroolsParserException;
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

public class ValidationRuleTest {
	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;
	private static Logger logger = Logger.getLogger(ValidationRuleTest.class);
	@Before
	public void init() {
		// read second rule from String
		//String ruleFormat = "import java.util.Map import java.util.List rule \"Values present\" when $map: Map(this[\"name\"] == null || this[\"name\"].isEmpty() || this[\"project_type\"] == null || this[\"project_type\"].isEmpty() || this[\"start_date\"] == null || this[\"start_date\"].isEmpty() || this[\"end_date\"] == null || this[\"end_date\"].isEmpty()) $list: List() then $list.add(\"One of name, project_type, start_date, end_date cannot be empty\"); end";
		String ruleFormat = "import java.util.Map import java.util.List rule \"Values present\" when $map: Map(%s) $list: List() then $list.add(\"One of %s cannot be empty\"); end";
		String inputs = "name, project_type, start_date, end_date";
		
		String inputConditions = generateConditions(inputs);
		String myRule = String.format(ruleFormat, inputConditions, inputs);
		System.out.println("myRule=" + myRule);
		Resource myResource = ResourceFactory.newReaderResource(new StringReader(myRule));
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
	}

	private String generateConditions(String inputs) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		String fmt = "this[\"%s\"] == null || this[\"%s\"].isEmpty()";

		String[] sts = inputs.split(",");
		int cnt = 1;
		for(String st: inputs.split(",")){
			sb.append(String.format(fmt, st.trim(), st.trim()));
			if(cnt < sts.length){
				sb.append(" || ");
			}
			cnt++;
		}
		System.out.println("condition=" + sb.toString());
		return sb.toString();
	}

	@Test
	public void shouldFireAssertIfAllPresent() throws IOException, DroolsParserException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("name", "myproject");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "1-1-16");
		List<String> list = new ArrayList<String>();
		kSession.insert(mapa);
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 0;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
		assertEquals(list.size(), 0);
	}
	
	@Test
	public void shouldFireAssertIfAllPresentFailedWithEmpty() throws IOException, DroolsParserException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("name", "");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "1-1-16");
		List<String> list = new ArrayList<String>();
		kSession.insert(mapa);
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 1;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
		assertEquals(list.size(), 1);
	}
	
	@Test
	public void shouldFireAssertIfAllPresentFailedWithNull() throws IOException, DroolsParserException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "1-1-16");
		List<String> list = new ArrayList<String>();
		kSession.insert(mapa);
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 1;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
		assertEquals(list.size(), 1);
	}
}