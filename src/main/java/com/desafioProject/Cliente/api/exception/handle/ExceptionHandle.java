package com.desafioProject.Cliente.api.exception.handle;

import com.desafioProject.Cliente.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<DefaultException> handle(ClienteNotFoundException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente não foi encontrado");
        defaultException.setDataHora(LocalDateTime.now());
        defaultException.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(OperacaoNotFoundException.class)
    public ResponseEntity<DefaultException> handle(OperacaoNotFoundException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Operação não foi encontrada");
        defaultException.setDataHora(LocalDateTime.now());
        defaultException.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteExistsException.class)
    public ResponseEntity<DefaultException> handle(ClienteExistsException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente já cadastrado no nosso sistema.");
        defaultException.setDataHora(LocalDateTime.now());
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ContaExistsException.class)
    public ResponseEntity<DefaultException> handle(ContaExistsException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente com esse documento já existe!");
        defaultException.setDataHora(LocalDateTime.now());
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteSaldoException.class)
    public ResponseEntity<DefaultException> handle(ClienteSaldoException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Conta sem saldo disponível para saque!");
        defaultException.setDataHora(LocalDateTime.now());
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
}
