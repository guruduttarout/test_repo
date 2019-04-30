package com.example.demo;

import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.config.DroolsBeanFactory;
import com.example.demo.model.Customer;
import com.example.demo.model.Customer.CustomerType;
import com.example.demo.service.ProductService;
import com.example.demo.model.Product;

@SpringBootApplication
public class DemoApplication {
	private KieSession kSession;

	public static void main(String[] args) {
		 SpringApplication.run(DemoApplication.class, args);
		//DemoApplication application = new DemoApplication();
		//application.kie();
	}

	private void kie() {
		Resource resource = ResourceFactory.newClassPathResource("rule/Discount.xls", getClass());
		kSession = new DroolsBeanFactory().getKieSession(resource);
		Customer customer = new Customer(CustomerType.INDIVIDUAL, 5);
		kSession.insert(customer);

		kSession.fireAllRules();
		kSession.dispose();
		System.err.println(customer.getDiscount());
		ProductService productService = new ProductService();
		
		Product product = new Product("Microwave", "Electronic");
        product = productService.applyLabelToProduct(product);
	}

}
