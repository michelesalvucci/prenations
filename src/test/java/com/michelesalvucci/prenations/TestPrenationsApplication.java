package com.michelesalvucci.prenations;

import org.springframework.boot.SpringApplication;

public class TestPrenationsApplication {

	public static void main(String[] args) {
		SpringApplication.from(PrenationsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
