package com.legendme.missions_svc;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LegendmeMissionsSvcApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.load();
//
//		System.setProperty("DB_URL", dotenv.get("DB_URL"));
//		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//		System.setProperty("API_KEY", dotenv.get("API_KEY"));

		SpringApplication.run(LegendmeMissionsSvcApplication.class, args);
	}


}
