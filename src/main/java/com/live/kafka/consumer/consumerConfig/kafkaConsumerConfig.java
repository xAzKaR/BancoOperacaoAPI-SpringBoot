package com.live.kafka.consumer.consumerConfig;

import com.live.kafka.consumer.dto.Conta;
import com.live.kafka.consumer.dto.Operacao;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class kafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Conta> contaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Conta.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Conta> containerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Conta> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contaConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Operacao> operacaoConsumerFactory() {
        Map<String, Object> propsi = new HashMap<>();
        propsi.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        propsi.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsi.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsi.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(propsi, new StringDeserializer(), new JsonDeserializer<>(Operacao.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Operacao> factories(){
        ConcurrentKafkaListenerContainerFactory<String, Operacao> factories = new ConcurrentKafkaListenerContainerFactory<>();
        factories.setConsumerFactory(operacaoConsumerFactory());
        return factories;
    }
}
