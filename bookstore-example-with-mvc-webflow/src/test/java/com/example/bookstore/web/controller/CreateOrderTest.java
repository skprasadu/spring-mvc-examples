package com.example.bookstore.web.controller;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public class CreateOrderTest extends AbstractXmlFlowExecutionTests {

    private OrderController orderController;

	protected void setUp() {
		orderController = Mockito.mock(OrderController.class);
	}

	@Override
	protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
		builderContext.registerBean("orderController", orderController);
	}
	
	@Override
	protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
		return resourceFactory.createFileResource("src/main/webapp/WEB-INF/view/public/createOrders/createOrders-flow.xml");
	}

	public void testStartCreateOrderFlow() {

		MutableAttributeMap input = new LocalAttributeMap();
		MockExternalContext context = new MockExternalContext();
		startFlow(input, context);

		Mockito.verify(orderController, VerificationModeFactory.times(1)).initializeForm();
		Mockito.verify(orderController, VerificationModeFactory.times(1)).initializeSelectableCategories();

		assertCurrentStateEquals("selectCategory");
		assertResponseWrittenEquals("selectCategory", context);
	}

	public void testSelectCategory_Next() {
		setCurrentState("selectCategory");
		OrderForm orderForm = createTestOrderForm();
		getFlowScope().put("orderForm", orderForm);

		MockExternalContext context = new MockExternalContext();
		context.setEventId("next");
		resumeFlow(context);
		Mockito.verify(orderController, VerificationModeFactory.times(1)).initializeSelectableBooks(orderForm);
		assertCurrentStateEquals("selectBooks");
		assertResponseWrittenEquals("selectBooks", context);
	}
	
	public void testSelectCategory_Cancel() {
		setCurrentState("selectCategory");
		getFlowScope().put("orderForm", createTestOrderForm());

		MockExternalContext context = new MockExternalContext();
		context.setEventId("cancel");
		resumeFlow(context);

		assertFlowExecutionEnded();
	}

	public void testSelectBook_AddAndNext() {
		setCurrentState("selectBooks");
		OrderForm orderForm = createTestOrderForm();
		getFlowScope().put("orderForm", orderForm);

		MockExternalContext context = new MockExternalContext();
		context.setEventId("add");
		resumeFlow(context);

		//Assert if book was saved
        Mockito.verify(orderController, VerificationModeFactory.times(1)).addBook(orderForm);

		context.setEventId("next");
		resumeFlow(context);		
	}

	
	public void testSelectDeliveryOptions_AddAndNext() {
		setCurrentState("selectDeliveryOptions");
		getFlowScope().put("orderForm", createTestOrderForm());
		getFlowDefinitionRegistry().registerFlowDefinition(createMockBookingSubflow());
		
		MockExternalContext context = new MockExternalContext();
		context.setEventId("finish");
		resumeFlow(context);
	}

	private Flow createMockBookingSubflow() {
	    Flow mockBookingFlow = new Flow("secured/placeOrders");
	    mockBookingFlow.setInputMapper(new Mapper() {
	        public MappingResults map(Object source, Object target) {
	            // assert that 1L was passed in as input
	            return null;
	        }
	    });
	    // immediately return the bookingConfirmed outcome so the caller can respond
	    new EndState(mockBookingFlow, "endOrderOk");
	    return mockBookingFlow;
	}

	private OrderForm createTestOrderForm() {
		OrderForm orderForm = new OrderForm();
		return orderForm;
	}
}