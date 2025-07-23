package com.bomgo.chat_agent;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatAgentApplication {

	@Value("${meta.webhook.validate.token}")
	private String metaWebhookValidateToken;

	public static void main(String[] args) {
		SpringApplication.run(ChatAgentApplication.class, args);
	}

	@Bean
	public Function<String, String> webhookValidation() {
		System.out.println("oi");
		return input -> input.toUpperCase();
	}
}