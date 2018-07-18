package com.fnra.order.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fnra.order.exception.OrderProcessingException;

public class MailSenderImpl implements MailSender {

	private static final String MAILHOST_FINRA = "mailhost.finra.com";
	private static final String ORDER_DEPT_EMAIL = "order_dept@finra.com";
	private static final String SHIPPING_DEPT_EMAIL = "shipping_dept@finra.com";

	@Override
	public boolean sendEmail(String productId, int quantity, String shippingAddr) {
		boolean mailSent = false;
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", MAILHOST_FINRA);
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ORDER_DEPT_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(SHIPPING_DEPT_EMAIL));
			message.setSubject("Order ready for shipment");
			message.setText(String.format("Ship %d quantity of the product -> %s to Address -> %s", quantity, productId,
					shippingAddr));
			Transport.send(message);
			mailSent = true;
			System.out.println("Email sent successfully....");
		} catch (Exception e) {
			OrderProcessingException.throwEx("Exception occured while sending shipment email!", e);
		}
		return mailSent;
	}

}
