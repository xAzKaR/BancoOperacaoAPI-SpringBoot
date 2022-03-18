package com.desafioProject.Cliente.api.exception.handle;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DefaultException {
    private int status;

    private String mensagem;

    private String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
}
