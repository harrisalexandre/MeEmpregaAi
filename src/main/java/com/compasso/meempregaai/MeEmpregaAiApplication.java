package com.compasso.meempregaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MeEmpregaAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeEmpregaAiApplication.class, args);
	}
}
