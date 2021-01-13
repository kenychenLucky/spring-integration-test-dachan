package com.dachan.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/integration/integration.xml")
public class IntegrationApplication {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = new SpringApplication(IntegrationApplication.class).run(args);
		System.out.println("hit to input terminal.");
		System.in.read();
		ctx.close();
	}

}
