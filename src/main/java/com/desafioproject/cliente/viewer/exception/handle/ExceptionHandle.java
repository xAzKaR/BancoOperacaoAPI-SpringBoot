package com.desafioproject.cliente.viewer.exception.handle;

import com.desafioproject.cliente.viewer.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<DefaultException> handle(ClienteNotFoundException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente não foi encontrado");
        defaultException.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(OperacaoNotFoundException.class)
    public ResponseEntity<DefaultException> handle(OperacaoNotFoundException e){

        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Operação não foi encontrada");
        defaultException.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteExistsException.class)
    public ResponseEntity<DefaultException> handle(ClienteExistsException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente já cadastrado no nosso sistema.");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ContaExistsException.class)
    public ResponseEntity<DefaultException> handle(ContaExistsException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente com esse documento já existe!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteSaldoException.class)
    public ResponseEntity<DefaultException> handle(ClienteSaldoException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Conta sem saldo disponível para saque!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteNotBeNullException.class)
    public ResponseEntity<DefaultException> handle(ClienteNotBeNullException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente não pode ser nulo!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteExistsByCnpjException.class)
    public ResponseEntity<DefaultException> handle(ClienteExistsByCnpjException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente com esse CNPJ já cadastrado");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteCpfInvalidoException.class)
    public ResponseEntity<DefaultException> handle(ClienteCpfInvalidoException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("CPF Inválido!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteCnpjInvalidoException.class)
    public ResponseEntity<DefaultException> handle(ClienteCnpjInvalidoException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("CNPJ Inválido!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteDocumentoNotBeNullException.class)
    public ResponseEntity<DefaultException> handle(ClienteDocumentoNotBeNullException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Cliente deve possuir ao menos um documento no nosso sistema!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<DefaultException> handle(ContaNotFoundException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Conta não existe!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteNegativoOrZeroException.class)
    public ResponseEntity<DefaultException> handle(ClienteNegativoOrZeroException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Valor de transação não pode ser negativo ou igual a zero!");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(OperacaoNaoCompletadaException.class)
    public ResponseEntity<DefaultException> handle(OperacaoNaoCompletadaException e){
        DefaultException defaultException = new DefaultException();
        defaultException.setMensagem("Serviço indisponível no momento");
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }



}
