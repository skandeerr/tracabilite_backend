package com.tracabilite.tracabilite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "")
@Configuration
@ComponentScan(basePackages = {"com.tracabilite.tracabilite.*"})
@EnableAspectJAutoProxy
@EntityScan("com.tracabilite.tracabilite.persistence.model")
@EnableJpaRepositories({"com.tracabilite.tracabilite.persistence.dao"})
@EnableConfigurationProperties
@EnableTransactionManagement
public class TracabiliteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracabiliteApplication.class, args);
	}

}
