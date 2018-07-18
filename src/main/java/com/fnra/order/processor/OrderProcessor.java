package com.fnra.order.processor;

public interface OrderProcessor {

	public boolean processOrder(String creditCardNumber, String productId, int quantity, String shippingAddr);
}
