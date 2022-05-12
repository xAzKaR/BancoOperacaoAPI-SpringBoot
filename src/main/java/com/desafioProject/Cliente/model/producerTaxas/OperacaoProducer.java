package com.desafioProject.Cliente.model.producerTaxas;

import com.desafioProject.Cliente.model.entity.Operacao;
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
public class OperacaoProducer {

    private static final Logger logger = LoggerFactory.getLogger(Operacao.class);
    private final String topic;
    private final KafkaTemplate<String, Operacao> kaffkaTemplate;

    public OperacaoProducer(@Value("${topic.name2}") String topic, @Qualifier("kaffkaTemplate") KafkaTemplate<String, Operacao> kafkaTemplate) {
        this.topic = topic;
        this.kaffkaTemplate = kafkaTemplate;
    }

    public void enviar(Operacao operacao) throws ExecutionException, InterruptedException, TimeoutException {
        kaffkaTemplate.send(topic, operacao).get(2, TimeUnit.SECONDS);

//        kaffkaTemplate.send(topic, operacao).addCallback(
//                success -> logger.info("Message send" + success.getProducerRecord().value()),
//                failure -> logger.info("Message failed" + failure.getMessage())
//        );
    }
}
