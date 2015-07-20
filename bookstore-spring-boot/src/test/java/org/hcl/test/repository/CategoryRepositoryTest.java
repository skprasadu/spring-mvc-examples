package org.hcl.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.hcl.test.UiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@DatabaseSetup(CategoryRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { CategoryRepositoryTest.DATASET })
@DirtiesContext
public class CategoryRepositoryTest {
	protected static final String DATASET = "classpath:datasets/it-categories.xml";
	private static final String FIRST_ITEM = "Item 1";
	private static final String THIRD_ITEM = "Item 2";
	private static final String DESCRIPTION_FIELD = "name";
	
	@Autowired
	private CategoryRepository repository;

	@Test
	public void findCheckedShouldReturnTwoItems() {
		assertThat(repository.findAll()).hasSize(2).extracting(DESCRIPTION_FIELD).containsOnly(FIRST_ITEM, THIRD_ITEM);
	}
}
