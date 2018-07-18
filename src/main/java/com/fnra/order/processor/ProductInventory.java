package com.fnra.order.processor;

public class ProductInventory {

	private final String productId;
	private final double unitPrice;
	private final int quantity;

	public ProductInventory(String productId, double unitPrice, int quantity) {
		super();
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

}
