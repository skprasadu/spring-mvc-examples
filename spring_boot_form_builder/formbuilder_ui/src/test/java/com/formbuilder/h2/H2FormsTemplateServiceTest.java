package com.formbuilder.h2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formbuilder.Application;
import com.formbuilder.FormInformationService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class H2FormsTemplateServiceTest {

	@Autowired
	FormInformationService formsService;

	@Before
	public void init() throws IOException {
		assertNotNull(formsService);
	}
	
	@Test
	public void findAllTemplates() throws Exception {
		List<Map> list = formsService.findAllFormTemplates("");
		assertEquals(2, list.size());
	}
}
