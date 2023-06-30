package com.desafioproject.cliente.model.producerTaxas;

import com.desafioproject.cliente.model.entity.Operacao;
import lombok.extern.slf4j.Slf4j;
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

    private final String topic;
    private final KafkaTemplate<String, Operacao> kaffkaTemplate;

    public OperacaoProducer(@Value("${topic.name2}") String topic, @Qualifier("kaffkaTemplate") KafkaTemplate<String, Operacao> kafkaTemplate) {
        this.topic = topic;
        this.kaffkaTemplate = kafkaTemplate;
    }

    public void enviar(Operacao operacao) throws ExecutionException, InterruptedException, TimeoutException {
        kaffkaTemplate.send(topic, operacao).get(2, TimeUnit.SECONDS);
    }
}
