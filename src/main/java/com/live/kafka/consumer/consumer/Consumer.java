package com.live.kafka.consumer.consumer;

import com.live.kafka.consumer.dto.Conta;
import com.live.kafka.consumer.dto.Operacao;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Value(value = "${topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "containerFactory")
    public void listenTopicConta(ConsumerRecord<String, Conta> record) {
        log.info("Received Message" + record.partition());
        log.info("Received Message" + record.value());
    }

    @KafkaListener(topics = "${topic.name2}", groupId = "${spring.kafka.group-id}", containerFactory = "factories")
    public void listenTopicOperacao(ConsumerRecord<String, Operacao> record) {
        log.info("Received Message: " + record.partition());
        log.info("Received Message: " + record.value());
    }
}
