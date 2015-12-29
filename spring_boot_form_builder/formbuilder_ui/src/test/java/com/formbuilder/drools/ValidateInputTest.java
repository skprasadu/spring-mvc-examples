package com.formbuilder.drools;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedHashMap;

import lombok.val;

import org.apache.log4j.Logger;
import org.drools.compiler.compiler.DroolsParserException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formbuilder.Application;
import com.formbuilder.UiRuleValidatorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ValidateInputTest {
	
	@Autowired
	UiRuleValidatorServiceImpl uiRuleValidatorServiceImpl;
	
	private static Logger logger = Logger.getLogger(ValidateInputTest.class);
	
	@Test
	public void shouldFireAssertIfAllPresent() throws IOException, DroolsParserException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		//Get Rule infomration for a Given Form
		
		val mapa = new LinkedHashMap<String, String>();
		mapa.put("name", "myproject");
		mapa.put("project_type", "billable");
		mapa.put("start_date", "1-1-15");
		mapa.put("end_date", "1-1-16");
		
		val list = uiRuleValidatorServiceImpl.evaluate("vendor_management", "project", mapa);

		//Create a Rule object using reflection
		// and pass both the Drools information and application specific data
		//It build the Final Drools data and returns to service layer
		//Validation Service layer executes the rule and gets the Error object
		
		assertEquals(list.size(), 0);
	}
	
	@Test
	public void shouldFireAssertIfAllPresentFailed() throws IOException, DroolsParserException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		//Get Rule infomration for a Given Form
		
		val mapa = new LinkedHashMap<String, String>();
		mapa.put("project_type", "billable");
		mapa.put("end_date", "1-1-16");
		
		val list = uiRuleValidatorServiceImpl.evaluate("vendor_management", "project", mapa);

		//Create a Rule object using reflection
		// and pass both the Drools information and application specific data
		//It build the Final Drools data and returns to service layer
		//Validation Service layer executes the rule and gets the Error object
		
		assertEquals(list.size(), 1);
	}

	@Test
	public void shouldFireAssertIfAllPresentFailedWithJsonObj() throws IOException, DroolsParserException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		//Get Rule infomration for a Given Form
		
		val mapa = new JSONObject();
		mapa.put("project_type", "billable");
		mapa.put("end_date", "1-1-16");
		
		val list = uiRuleValidatorServiceImpl.evaluate("vendor_management", "project", mapa);

		//Create a Rule object using reflection
		// and pass both the Drools information and application specific data
		//It build the Final Drools data and returns to service layer
		//Validation Service layer executes the rule and gets the Error object
		
		assertEquals(list.size(), 1);
	}
}