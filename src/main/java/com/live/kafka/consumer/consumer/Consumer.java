package com.live.kafka.consumer.consumer;

import com.live.kafka.consumer.dto.Conta;
import com.live.kafka.consumer.dto.Operacao;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import com.live.kafka.consumer.repository.ContaRepository;
import com.live.kafka.consumer.repository.OperacaoRepository;

@RequiredArgsConstructor
@Component
public class Consumer {

    private final ContaRepository contatRepository;
    private final OperacaoRepository operacaotRepository;
    private final Jedis jedis = new Jedis();
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);


    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "containerFactory")
    public void listenTopicConta(ConsumerRecord<String, Conta> record) {
        log.info("Received Message" + record.partition());
        log.info("Received Message" + record.value());

        Conta conta = record.value();
        jedis.set(conta.getNumeroDaConta(),
                Integer.toString(conta.getSaqueSemTaxa()));

        contatRepository.save(conta);
    }

    @KafkaListener(topics = "${topic.name2}", groupId = "${spring.kafka.group-id}", containerFactory = "factories")
    public void listenTopicOperacao(ConsumerRecord<String, Operacao> record) {
        log.info("Received Message: " + record.partition());
        log.info("Received Message: " + record.value());

        var operacao = record.value();

        var conta = contatRepository.findContaByNumeroDaConta(operacao.getNumeroConta());

        conta.setSaldo(operacao.getSaldo());

        Integer quantidadeDeSaques = Integer.parseInt(jedis.get(operacao.getNumeroConta()));
        if (quantidadeDeSaques > 0) {
            conta.setSaqueSemTaxa(quantidadeDeSaques -1);
        } else {
            conta.setSaqueSemTaxa(quantidadeDeSaques);
        }

        contatRepository.save(conta);
        operacaotRepository.save(operacao);
        System.out.println(conta);
    }
}
