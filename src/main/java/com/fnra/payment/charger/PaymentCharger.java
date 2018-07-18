package com.fnra.payment.charger;

public interface PaymentCharger {
	boolean chargePayment(String creditCardNumber, double amount);
}
