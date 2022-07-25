package com.example.admin.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.example.petshop.common.entity", "com.example.petshop.admin.user"})
public class PetShopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetShopBackEndApplication.class, args);
	}

}
