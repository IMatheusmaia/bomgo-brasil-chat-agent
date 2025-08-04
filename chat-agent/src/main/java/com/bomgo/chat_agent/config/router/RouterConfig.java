package com.bomgo.chat_agent.config.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.FunctionProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

@Configuration
public class RouterConfig {

    @Bean
    @SuppressWarnings("unchecked")
	public MessageRoutingCallback routingCallback() {
		return new MessageRoutingCallback() {
			@Override
			public String routingResult(Message<?> message) {
				try {
					Map<String, String> params = (Map<String, String>) message.getHeaders().getOrDefault(HeaderUtils.HTTP_REQUEST_PARAM, new HashMap<>());

                    String mode = params.get("hub.mode");
                    
					if (mode.equals("subscribe")) {
						return new String("webhookValidation");
					}
						return new String("webhookEventHandler");

				} catch (NullPointerException e) {
					return new String("webhookEventHandler");
				}
			}
		};
	}

	@Bean
	public RoutingFunction routingFunction(FunctionCatalog functionCatalog, BeanFactory beanFactory, @Nullable MessageRoutingCallback routingCallback) {
		
		FunctionProperties properties = new FunctionProperties();
        // properties.setRoutingExpression("headers['hub.mode'] == 'subscribe' ? 'webhookValidation' : 'webhookEventHandler'");
		properties.setDefinition("webhookValidation");
		properties.setDefinition("webhookEventHandler");

		return new RoutingFunction(functionCatalog, properties);
	}

    
}
