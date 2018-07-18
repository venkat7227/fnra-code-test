package com.fnra.order.processor;

import java.net.URISyntaxException;
import java.util.Map;

import com.fnra.order.exception.OrderProcessingException;
import com.fnra.order.mail.MailSender;
import com.fnra.order.util.OrderProcessorUtils;
import com.fnra.payment.charger.PaymentCharger;

public class OrderProcessorImpl implements OrderProcessor {

	private final PaymentCharger paymentCharger;
	private final MailSender mailSender;

	public OrderProcessorImpl(final PaymentCharger paymentCharger, final MailSender mailSender) {
		super();
		this.paymentCharger = paymentCharger;
		this.mailSender = mailSender;
	}

	@Override
	public boolean processOrder(String creditCardNumber, String productId, int quantity, String shippingAddr) {
		boolean orderProcessed = false;
		try {
			Map<String, ProductInventory> productInventoryMap = OrderProcessorUtils.productInventoryCsvtoMap();
			if (checkInventory(productInventoryMap, productId, quantity)) {
				double amount = productInventoryMap.get(productId).getUnitPrice() * quantity;
				if (paymentCharger.chargePayment(creditCardNumber, amount)) {
					orderProcessed = mailSender.sendEmail(productId, quantity, shippingAddr);
				} else {
					OrderProcessingException.throwEx("Credit Card Validation Failed!");
				}
			}
		} catch (URISyntaxException e) {
			OrderProcessingException.throwEx("Exception Occured while processing Order!", e);
		}
		return orderProcessed;
	}

	public boolean checkInventory(Map<String, ProductInventory> productInventoryMap, String productId, int quantity) {
		boolean inventoryAvailable = false;
		ProductInventory productInventory = productInventoryMap.get(productId);
		if ((productInventory != null) && (productInventory.getQuantity() >= quantity)) {
			inventoryAvailable = true;
		}
		return inventoryAvailable;
	}

}
