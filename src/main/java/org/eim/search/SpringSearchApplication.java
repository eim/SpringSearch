package org.eim.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.eim.search.repository")
@EnableTransactionManagement
public class SpringSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSearchApplication.class, args);
	}
}
