package com.formbuilder;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formbuilder.dao.ListInformation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FormsTemplateServiceTest {

	@Autowired
	FormInformationService formsService;

	@Before
	public void init() throws IOException {
		assertNotNull(formsService);
	}
	
	@Test
	public void findAllTemplates() throws Exception {
		List<ListInformation> list = formsService.findAllFormTemplates();
		assertEquals(7, list.size());
	}
}
