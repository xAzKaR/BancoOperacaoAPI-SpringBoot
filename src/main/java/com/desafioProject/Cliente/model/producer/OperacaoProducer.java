package com.desafioProject.Cliente.model.producer;

import com.desafioProject.Cliente.model.entity.Operacao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OperacaoProducer {

    private static final Logger logger = LoggerFactory.getLogger(Operacao.class);
    private final String topic;
    private final KafkaTemplate<String, Operacao> kafkaTemplate;

    public OperacaoProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, Operacao> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(Operacao operacao){
        kafkaTemplate.send(topic, operacao).addCallback(
                success -> logger.info("Message send" + success.getProducerRecord().value()),
                failure -> logger.info("Message failed" + failure.getMessage())
        );
    }
}
