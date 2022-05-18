package com.example.spring.shoppinglist.ApplicationClasses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EntityScan("com.example.spring.shoppinglist.EntityClasses")

public class Application {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingListRestController.class, args);
	}
}
