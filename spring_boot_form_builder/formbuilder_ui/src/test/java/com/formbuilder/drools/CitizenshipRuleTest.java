package com.formbuilder.drools;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class CitizenshipRuleTest {
	KieSession kSession;
	
	@Before
	public void init(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		kSession = kContainer.newKieSession("ksession1");
	}

	@Test
	public void shouldFireCitizenshipDate() throws IOException, DroolsParserException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("citizenship_date", "");
		List<String> list = new ArrayList<String>();
		kSession.insert(mapa);
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 1;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
	}
	
	@Test
	public void shouldFireCitizenshipDateAndUS() throws IOException, DroolsParserException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("citizenship_date", "");
		mapa.put("country", "US");
		List<String> list = new ArrayList<String>();
		kSession.insert(mapa);
		kSession.insert(list);
		
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 2;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
		assertEquals(list.size(), 2);
		assertTrue(list.contains("Date can be empty if US"));
	}
}