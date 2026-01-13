package com.patrick.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {

    @Bean
    public NewTopic emailCodeTopic() {
        return TopicBuilder
                .name("email-code")
                .build();
    }

    @Bean
    public NewTopic userConfirmedTopic() {
        return TopicBuilder
                .name("user-confirmed")
                .build();
    }
}
