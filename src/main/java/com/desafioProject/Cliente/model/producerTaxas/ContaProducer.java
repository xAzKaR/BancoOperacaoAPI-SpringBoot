package com.desafioProject.Cliente.model.producerTaxas;

import com.desafioProject.Cliente.model.entity.Conta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class ContaProducer {

    private static final Logger logger = LoggerFactory.getLogger(Conta.class);
    private final String topic;
    private final KafkaTemplate<String, Conta> kafkaTemplate;

    public ContaProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, Conta> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(Conta conta) throws ExecutionException, InterruptedException, TimeoutException {
            kafkaTemplate.send(topic, conta).get(2, TimeUnit.SECONDS);
    }

//    public void enviar(Conta conta) {
//            kafkaTemplate.send(topic, conta).addCallback(
//                    success -> logger.info("Mensagem enviada: " + success.getProducerRecord().value()),
//                    failure -> logger.info("Houve falha no envio: " + failure.getMessage())
//            );
//    }
}

