package com.formbuilder.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import lombok.val;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formbuilder.Application;
import com.formbuilder.FormInformationService;
import com.formbuilder.UiRuleValidatorService;
import com.formbuilder.dto.FormInformation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MongoFormsTemplateServiceTest {

	@Autowired
	FormInformationService formsService;
	@Autowired
	UiRuleValidatorService uiRuleValidatorServiceImpl;

	@Before
	public void init() throws IOException {
		assertNotNull(formsService);
		formsService.deleteAll();
		val payload = readFile("src/test/resources/schema/form-template.json", Charset.defaultCharset());
		val om = new ObjectMapper();
		val formTemplate = om.readValue(payload, FormInformation.class);
		formTemplate.setEntryType("Form");
		formTemplate.setApplication("vendor_management");
		try {
			formsService.saveForm(formTemplate, "vendor_management", "fuelload");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//UiRule rule = new UiRule(null, "fuelload",
		//		"{\"action\": \"assertIfOneIsPresent\", \"input\":\"citizen_country_group__country_group_id, citizen__country_id\"}");
		//uiRuleValidatorServiceImpl.save(rule);
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Test
	public void testFindAll() throws Exception {
		List<Map> list = formsService.findAllFormTemplates("vendor_management");

		assertNotNull(list);
		assertEquals(list.size(), 1);

		assertEquals(list.get(0).get("group"), "Form");
		assertNotNull(list.get(0).get("tableList"));
	}

	@Ignore
	@Test
	public void testFindByName() throws JsonParseException, JsonMappingException, IOException {
		val formTemplate = formsService.getData("vendor_management", "fuelload", "0");

		assertNotNull(formTemplate);
	}
}
