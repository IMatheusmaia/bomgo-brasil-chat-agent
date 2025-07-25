package com.bomgo.chat_agent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class ChatAgentApplication {

	@Value("${meta.webhook.validate.token}")
	private String metaWebhookValidateToken;

	public static void main(String[] args) {
		SpringApplication.run(ChatAgentApplication.class, args);
	}

	@SuppressWarnings("unchecked")
	@Bean
	public Function<Message<Object>, String> webhookValidation() {
		return input -> {
			Map<String, String> params = new HashMap<>();

			try {
				params = (Map<String, String>) input.getHeaders()
					.getOrDefault(HeaderUtils.HTTP_REQUEST_PARAM, new HashMap<String, String>());

				if (input.getPayload().equals("")) {
					String challenge = params.get("hub.challenge");
					String verifyToken = params.get("hub.verify_token");
					String mode = params.get("hub.mode");

	
					if (verifyToken.equals(this.metaWebhookValidateToken) && mode.equals("subscribe")) {
						return challenge;

					} else {
						throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid verify token");
					}
				}
				return "";
	
			} catch (Exception e) {
				throw new RuntimeException("error validating webhook");
			}
		};
	}
}