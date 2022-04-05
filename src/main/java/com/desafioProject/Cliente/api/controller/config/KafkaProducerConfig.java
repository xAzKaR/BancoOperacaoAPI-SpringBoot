package com.desafioProject.Cliente.api.controller.config;

import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.Operacao;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.name}")
    private String topic;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, 3, (short) 1);
    }

    @Bean
    public ProducerFactory<String, Operacao> OperacaoProducerFactory() {
        return getStringProducerFactory();
    }

    @Bean
    public ProducerFactory<String, Conta> ContaProducerFactory() {
        return getStringProducerFactory();
    }

    private <T> ProducerFactory<String, T> getStringProducerFactory() {
        Map<String, Object> configOp = new HashMap<>();
        configOp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configOp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configOp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configOp);
    }

    @Bean
    public KafkaTemplate<String, Conta> kafkaTemplate(){
        return new KafkaTemplate<>(ContaProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Operacao> kaffkaTemplate(){
        return new KafkaTemplate<>(OperacaoProducerFactory());
    }

}
