package com.fnra.order.processor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fnra.order.exception.OrderProcessingException;
import com.fnra.order.mail.MailSender;
import com.fnra.payment.charger.PaymentCharger;

@RunWith(MockitoJUnitRunner.class)
public class OrderProcessorImplTest {
	@Mock
	private PaymentCharger paymentCharger;
	@Mock
	private MailSender mailSender;

	private OrderProcessorImpl orderProcessorImpl;

	@Before
	public void setUp() throws Exception {
		orderProcessorImpl = new OrderProcessorImpl(paymentCharger, mailSender);
	}

	@Test
	public void testProcessOrderForAvailableQuantity() {
		String creditCardNumber = "123456789";
		String productId = "prod1";
		int quantity = 10;
		String shippingAddr = "123 fnra st";

		when(paymentCharger.chargePayment(creditCardNumber, 125)).thenReturn(true);
		when(mailSender.sendEmail(productId, quantity, shippingAddr)).thenReturn(true);
		assertTrue(orderProcessorImpl.processOrder(creditCardNumber, productId, quantity, shippingAddr));
	}

	@Test
	public void testProcessOrderForNonAvailableQuantity() {
		String creditCardNumber = "123456789";
		String productId = "prod1";
		int quantity = 11;
		String shippingAddr = "123 fnra st";

		assertFalse(orderProcessorImpl.processOrder(creditCardNumber, productId, quantity, shippingAddr));
	}

	@Test(expected = OrderProcessingException.class)
	public void testProcessOrderForFailedCreditCardValidation() {
		String creditCardNumber = "123456789000000";
		String productId = "prod1";
		int quantity = 10;
		String shippingAddr = "123 fnra st";
		when(paymentCharger.chargePayment(creditCardNumber, 125)).thenReturn(false);
		orderProcessorImpl.processOrder(creditCardNumber, productId, quantity, shippingAddr);
	}

	@Test
	public void testProcessOrderForNonAvailableProductId() {
		String creditCardNumber = "123456789";
		String productId = "prod1-invalid";
		int quantity = 10;
		String shippingAddr = "123 fnra st";

		assertFalse(orderProcessorImpl.processOrder(creditCardNumber, productId, quantity, shippingAddr));
	}

}
