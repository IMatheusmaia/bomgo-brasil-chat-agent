package com.bomgo.chat_agent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetaWebhookEventPayload(
    String object,
    List<Entry> entry
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Entry(
        String id,
        List<Change> changes
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Change(
            Value value,
            String field
        ) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Value(
                String messaging_product,
                Metadata metadata,
                Contacts contacts,
                Messages messages
            ) {
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Metadata(
                    String display_phone_number,
                    String phone_number_id
                ) {}

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Contacts(
                    String wa_id,
                    String user_id,
                    Profile profile
                ) {
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public record Profile(
                        String name
                    ) {}
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Messages(
                    Audio audio,
                    String from,
                    String id,
                    Text text,
                    String type
                ) {
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public record Audio(
                        String id,
                        String mime_type
                    ) {}

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public record Text(
                        String body
                    ) {}
                }
            }
        }
    }
}