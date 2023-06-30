package com.desafioproject.cliente.model.producerTaxas;

import com.desafioproject.cliente.model.entity.Conta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class ContaProducer {

    private final String topic;
    private final KafkaTemplate<String, Conta> kafkaTemplate;

    public ContaProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, Conta> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(Conta conta) throws ExecutionException, InterruptedException, TimeoutException {
            kafkaTemplate.send(topic, conta).get(2, TimeUnit.SECONDS);
    }
}

