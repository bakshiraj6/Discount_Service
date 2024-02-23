package com.raj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableBinding(Processor.class)
public class DiscountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountServiceApplication.class, args);
	}
	Logger logger= LoggerFactory.getLogger(DiscountServiceApplication.class);
	public double calPrice(Double price, double disc)
	{

		double fp=price*(100-disc)*0.01;
		logger.info("Final Price set to {} for disc {}",fp,disc);
		return fp;
	}
	@Transformer(inputChannel = Processor.INPUT,outputChannel = Processor.OUTPUT)
	List<Product> addDiscountToProduct(List<Product> products)
	{
		List<Product> cp=new ArrayList<>();
		logger.info("In Discount Service");
		for (Product x :
				products) {
			if (x.getPrice()>8000)
			{
				double y=calPrice(x.getPrice(),10);
				x.setPrice(y);
				cp.add(x);

			} else if (x.getPrice()>5000) {
				double y=calPrice(x.getPrice(),5);
				x.setPrice(y);
				cp.add(x);
			}
		}
		return cp;
	}
}
