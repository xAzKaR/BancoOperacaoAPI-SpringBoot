package com.desafioProject.Cliente.api.controller;

import com.desafioProject.Cliente.api.dto.request.ContaDto;

import com.desafioProject.Cliente.api.dto.response.ContaResponse;
import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.repository.ContaRepository;
import com.desafioProject.Cliente.model.service.ContaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ContaResponse> salvar(@RequestBody @Valid ContaDto contaDto) {
        ContaResponse contaCriada = contaService.salvar(contaDto);
        return ResponseEntity.created(null).body(contaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> findById(@PathVariable("id") Long id) {
        ContaDto conta = contaService.localizarId(id);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/numeroDaConta/{numeroDaConta}")
    public ResponseEntity<ContaDto> locazalicarConta(@PathVariable("numeroDaConta") String numeroDaConta) {
        ContaDto conta = contaService.locazalicarConta(numeroDaConta);
        return ResponseEntity.ok(conta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        contaService.deletar(id);
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