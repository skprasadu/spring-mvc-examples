package com.formbuilder.drools;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

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

public class HelloDroolsNewTest {
	private KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	private Collection<KnowledgePackage> pkgs;
	private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	private StatefulKnowledgeSession kSession;

	@Before
	public void init() {
		// read second rule from String
		String myRule = "import com.formbuilder.drools.Message rule \"Hello World 2\" when message:Message (type==\"Test\") then System.out.println(\"Test, Drools!\"); end";
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

	@Test
	public void shouldFireCitizenshipDate() throws IOException, DroolsParserException {
		Message msg = new Message();
		msg.setType("Test");
		kSession.insert(msg);
		int actualNumberOfRulesFired = kSession.fireAllRules();
		int expectedNumberOfRulesFired = 1;
		assertThat(actualNumberOfRulesFired, is(expectedNumberOfRulesFired));
	}
}