package com.patrick.consumer.config;

import com.patrick.consumer.dto.UserCodeEvent;
import com.patrick.consumer.dto.UserConfirmedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {

    public Map<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return configProps;
    }

    @Bean
    public ConsumerFactory<String, UserCodeEvent> userCodeConsumerFactory() {
        Map<String, Object> configProps = consumerFactory();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UserCodeEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCodeEvent> userCodeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserCodeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userCodeConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserConfirmedEvent> userConfirmedConsumerFactory() {
        Map<String, Object> props = consumerFactory();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UserConfirmedEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserConfirmedEvent> userConfirmedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserConfirmedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConfirmedConsumerFactory());
        return factory;
    }
}
