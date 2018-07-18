package com.fnra.order.exception;


public final class OrderProcessingException extends RuntimeException {

	private OrderProcessingException(final String message) {
		super(message);
	}

	private OrderProcessingException(final String message,final Throwable cause) {
		super(message, cause);
	}

	public static OrderProcessingException throwEx(final String message) {
		throw new OrderProcessingException(message);
	}

	public static OrderProcessingException throwEx(final String message, final Throwable cause) {
		throw new OrderProcessingException(message, cause);
	}

}