package com.desafioProject.Cliente.model.producerMedico;

import com.desafioProject.Cliente.model.entity.Conta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class ContaProducerMedico {

    private static final Logger logger = LoggerFactory.getLogger(Conta.class);
    private final String topic;
    private final KafkaTemplate<String, Conta> kafrikaTemplate;

    public ContaProducerMedico(@Value("${topic.name4}") String topic, @Qualifier("kafrikaTemplate") KafkaTemplate<String, Conta> kafkaTemplate) {
        this.topic = topic;
        this.kafrikaTemplate = kafkaTemplate;
    }

    public void enviar(Conta conta) throws ExecutionException, InterruptedException, TimeoutException {
        kafrikaTemplate.send(topic, conta).get(2, TimeUnit.SECONDS);

//        kafrikaTemplate.send(topic, conta).addCallback(
//                success -> logger.info("Mensagem enviada: " + success.getProducerRecord().value()),
//                failure -> logger.info("Houve falha no envio: " + failure.getMessage())
//        );
    }
}

