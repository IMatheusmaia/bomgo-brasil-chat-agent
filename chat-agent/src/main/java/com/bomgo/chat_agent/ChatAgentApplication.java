package com.bomgo.chat_agent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.web.server.ResponseStatusException;

import com.bomgo.chat_agent.dto.MessageObject;
import com.bomgo.chat_agent.dto.MetaWebhookEventPayload;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

@SpringBootApplication
public class ChatAgentApplication {

	@Value("${meta.webhook.validate.token}")
	private String metaWebhookValidateToken;

	public static void main(String[] args) {
		SpringApplication.run(ChatAgentApplication.class, args);
	}

	// @SuppressWarnings("unchecked")
	// @Bean
	// public Function<Message<Object>, String> webhookValidation() {
	// 	return input -> {
	// 		Map<String, String> params = new HashMap<>();

	// 		try {
	// 			params = (Map<String, String>) input.getHeaders()
	// 				.getOrDefault(HeaderUtils.HTTP_REQUEST_PARAM, new HashMap<String, String>());

	// 			if (input.getPayload().equals("")) {
	// 				String challenge = params.get("hub.challenge");
	// 				String verifyToken = params.get("hub.verify_token");
	// 				String mode = params.get("hub.mode");

	
	// 				if (verifyToken.equals(this.metaWebhookValidateToken) && mode.equals("subscribe")) {
	// 					return challenge;

	// 				} else {
	// 					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid verify token");
	// 				}
	// 			}
	// 			return "";
	
	// 		} catch (Exception e) {
	// 			throw new RuntimeException("error validating webhook");
	// 		}
	// 	};
	// };

	@Bean
	public Function<String, String> webhookEventHandler() {
		return input -> {
			try {
				MetaWebhookEventPayload receivedPayload = JsonMapper
					.builder().build().readValue(input, MetaWebhookEventPayload.class);
				
				return MessageObject.processPayload(receivedPayload).toString();
				
			} catch (JsonProcessingException e) {
				return "Erro ao converter a mensagem de JSON para POJO. " + e.getMessage();

			} catch(IndexOutOfBoundsException e) {
				return "Erro ao processar o payload. " + e.getMessage();

			} catch(NullPointerException e) {
				return "Erro ao processar o payload. " + e.getMessage();
			}
		};
	};
}