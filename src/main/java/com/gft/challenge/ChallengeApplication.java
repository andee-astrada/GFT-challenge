package com.gft.challenge;

import com.gft.challenge.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ChallengeApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ChallengeApplication.class, args);

		PriceService service = context.getBean(PriceService.class);
		service.initialDataLoad();

	}

}
