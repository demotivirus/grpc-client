package com.example.grpcclient;

import ecommerce.ProductInfoGrpc;
import ecommerce.ProductInfoOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;

public class GrpcClientApplication {
	private static final Logger logger = Logger.getLogger(GrpcClientApplication.class.getName());

	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
				.usePlaintext()
				.build();

		ProductInfoGrpc.ProductInfoBlockingStub stub =
				ProductInfoGrpc.newBlockingStub(channel);

		ProductInfoOuterClass.ProductID productID = stub.addProduct(
				ProductInfoOuterClass.Product.newBuilder()
						.setName("Samsung S10")
						.setDescription("Samsung Galaxy S10 is the latest smart phone, " +
								"launched in February 2019")
						.setPrice(700.0f)
						.build());
		logger.info("Product ID: " + productID.getValue() + " added successfully.");

		ProductInfoOuterClass.Product product = stub.getProduct(productID);
		logger.info("Product: " + product.toString());
		channel.shutdown();
	}

}
