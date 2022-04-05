package com.live.kafka.consumer.service;

import com.live.kafka.consumer.dto.Conta;
import com.live.kafka.consumer.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Component
@EnableScheduling
@AllArgsConstructor
public class RenovarSaques {
    private final ContaRepository contaRepository;
    private final Jedis jedis = new Jedis();
    private final long MINUTOS = 60000;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Scheduled(fixedDelay = MINUTOS)
    public void renovarSaques() {
        String dataAtual = dateTimeFormatter.format(LocalDateTime.now());
        String ultimoDia = dateTimeFormatter.format(LocalDate.now().with(lastDayOfMonth()).atTime(23,59));

        if (dataAtual.equals(ultimoDia)) {
            List<Conta> contaList = contaRepository.findAll();

            for (Conta conta : contaList) {
                String key = Long.toString(conta.getId());
                String value = Integer.toString(conta.getSaqueSemTaxa());

                jedis.set(key, value);
            }
        }
    }
}
