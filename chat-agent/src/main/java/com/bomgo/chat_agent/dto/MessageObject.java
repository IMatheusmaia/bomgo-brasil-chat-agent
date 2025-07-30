package com.bomgo.chat_agent.dto;

import java.util.List;
import java.util.Objects;

public record MessageObject(
    ContactInfo contact,
    String from,
    List<String> textMessages,
    List<AudioInfo> audioMessages
) {
        public record ContactInfo(
        String wa_id,
        String user_id,
        ProfileInfo profile
    ) {
        public record ProfileInfo(String name) {}
        public ContactInfo(
            MetaWebhookEventPayload.Entry.Change.Value.Contact contact
        ) {
            this(
                contact.wa_id(),
                contact.user_id(),
                new ProfileInfo(contact.profile().name())
            );
        }
    }

        public record AudioInfo(
        String id,
        String mime_type
    ) {
        public AudioInfo(
            MetaWebhookEventPayload.Entry.Change.Value.Message.Audio audio
        ) {
            this(
                audio.id(),
                audio.mime_type()
            );
        }
    }

    public static MessageObject processPayload (MetaWebhookEventPayload payload) throws IndexOutOfBoundsException, NullPointerException {
        return  new MessageObject(
            new ContactInfo(
                payload.entry().get(0)
                .changes().get(0)
                .value().contacts().get(0)
            ),
            payload.entry().get(0)
                .changes().get(0)
                .value().messages().get(0).from(),
                payload.entry().stream().flatMap(
                    entry -> entry.changes().stream().flatMap(
                        change -> change.value().messages().stream()
                    )
                ).map(
                    message -> message.text().body()
                ).toList(),
                payload.entry().stream().flatMap(
                    entry -> entry.changes().stream().flatMap(
                        change -> change.value().messages().stream()
                    )
                ).map(
                    message -> {
                        if (Objects.nonNull(message.audio())) {
                            return new AudioInfo(message.audio());
                        } else {
                            return null;
                        }
                    }
                ).filter(nullValue -> nullValue != null).toList()
        );
    }
};
