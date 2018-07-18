package com.fnra.order.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fnra.order.exception.OrderProcessingException;
import com.fnra.order.processor.ProductInventory;

public final class OrderProcessorUtils {

	private OrderProcessorUtils() {

	}
	public static Map<String, ProductInventory> productInventoryCsvtoMap() throws URISyntaxException {
		Map<String, ProductInventory> resultMap = null;
		URI persistStoreUri = OrderProcessorUtils.class.getClassLoader().getResource("PRODUCT_INVENTORY.csv").toURI();
		try (Stream<String> lines = Files.lines(Paths.get(persistStoreUri)).skip(1)) {
			resultMap = lines.map(line -> line.split(","))
					.collect(Collectors.toMap(line -> line[0], line -> new ProductInventory(line[0], Double.valueOf(line[1]), Integer.parseInt(line[2]))));
		} catch (IOException e) {
			OrderProcessingException.throwEx("Exception occured while converting CSV to Map!", e);
		}
		return resultMap;
	}
}
