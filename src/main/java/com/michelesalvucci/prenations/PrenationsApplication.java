package com.michelesalvucci.prenations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.michelesalvucci.prenations.client.PNErrorDecoder;

import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = { "com.michelesalvucci.prenations",
		"org.springframework.security.oauth2.jwt" })
@Slf4j
@ComponentScan(basePackages = { "com.michelesalvucci.prenations" })
@EnableFeignClients(basePackages = "com.michelesalvucci.prenations.web.feign")
@Import(FeignClientsConfiguration.class)
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@EnableJpaRepositories({ "com.michelesalvucci.prenations.repository" })
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class PrenationsApplication {

	public static void main(String[] args) {
		log.info("Startup application with args {}", args.toString());
		SpringApplication.run(PrenationsApplication.class, args);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new PNErrorDecoder();
	}

	@Bean
	feign.Logger.Level feignLoggerLevel() {
		return feign.Logger.Level.BASIC;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
		return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
	}
}
