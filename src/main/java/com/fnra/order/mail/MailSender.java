package com.fnra.order.mail;

public interface MailSender {

	public boolean sendEmail(String productId, int quantity, String shippingAddr);

}
