package com.bomgo.chat_agent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetaWebhookEvent(
    String token,
    MetaWebhookEventPayload payload
) {
    
}
