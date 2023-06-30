package com.desafioproject.cliente.controller;

import com.desafioproject.cliente.viewer.dto.request.ContaDto;

import com.desafioproject.cliente.viewer.dto.response.ContaResponse;
import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.repository.ContaRepository;
import com.desafioproject.cliente.model.service.ContaService;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/conta")
@RestController
@Slf4j
public class ContaController {

    private final ContaService contaService;
    private final ContaRepository contaRepository;

    @PostMapping
    public ResponseEntity<ContaResponse> salvar(@RequestBody @Valid ContaDto contaDto) throws TimeoutException, OperacaoNaoCompletadaException, InterruptedException {
        ContaResponse contaCriada = contaService.salvar(contaDto);
        return ResponseEntity.created(null).body(contaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> findById(@PathVariable("id") Long id) {
        ContaDto conta = contaService.localizarId(id);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/numeroDaConta/")
    public ResponseEntity<ContaDto> locazalicarConta(@RequestParam("numeroDaConta") String numeroDaConta) {
        ContaDto conta = contaService.locazalicarConta(numeroDaConta);
        return ResponseEntity.ok(conta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar/")
    public ResponseEntity<Void> deletarNumeroDaConta(@RequestParam("numeroDaConta") String numeroDaConta) {
        contaService.deletarNumeroDaConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ContaDto>> listar(){
        List<ContaDto> listaClientes = contaService.listar();

        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/pagina/{numeroPagina}/{qtdePagina}")
    public ResponseEntity<Iterable<Conta>> listAll(@PathVariable int numeroPagina, @PathVariable int qtdePagina){
        Pageable page = PageRequest.of(numeroPagina, qtdePagina);

        return ResponseEntity.ok(contaRepository.findAll(page));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContaDto> atualizar(@RequestBody ContaDto contaDto, @PathVariable Long id) {
        ContaDto contaDtoAtualizado = contaService.atualizar(contaDto, id);
        return ResponseEntity.ok(contaDtoAtualizado);
    }
}