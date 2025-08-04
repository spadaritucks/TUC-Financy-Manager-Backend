package com.tucfinancymanager.backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.tucfinancymanager.backend.config.EnvConfig;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {
	public static void main(String[] args) {
		EnvConfig.loadEnvironmentVariables();
		SpringApplication.run(BackendApplication.class, args);
	}

}
