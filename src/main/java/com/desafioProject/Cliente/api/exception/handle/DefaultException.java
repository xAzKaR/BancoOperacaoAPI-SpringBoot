package com.desafioProject.Cliente.api.exception.handle;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DefaultException {
    private int status;

    private String mensagem;

    private LocalDateTime dataHora;
}
