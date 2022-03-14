package com.desafioProject.Cliente.api.controller;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;
import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.repository.OperacaoRepository;
import com.desafioProject.Cliente.model.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/operacao")
@RestController
@Slf4j
public class OperacaoController {

    private final OperacaoService operacaoService;
    private final OperacaoRepository operacaoRepository;

    @PostMapping("/depositar")
    public ResponseEntity<OperacaoDto> depositar(@RequestBody @Valid OperacaoDto operacaoDto) {
        operacaoService.depositar(operacaoDto);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/sacar")
    public ResponseEntity<OperacaoDto> sacar(@RequestBody @Valid OperacaoDto operacaoDto) {
        operacaoService.sacar(operacaoDto);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/transferir")
    public ResponseEntity<OperacaoDto> transferir(@RequestBody @Valid OperacaoDto operacaoDto) {
        operacaoService.transferir(operacaoDto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperacaoDto> findById(@PathVariable("id") Long id) {
        OperacaoDto operacaoDto = operacaoService.findById(id);
        return ResponseEntity.ok(operacaoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        operacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OperacaoDto>> listar(){
        List<OperacaoDto> listaOperacoes = operacaoService.listar();

        return ResponseEntity.ok(listaOperacoes);
    }

    @GetMapping("/pagina/{numeroPagina}/{qtdePagina}")
    public ResponseEntity<Iterable<Operacao>> listAll(@PathVariable int numeroPagina, @PathVariable int qtdePagina){
        Pageable page = PageRequest.of(numeroPagina, qtdePagina);

        return ResponseEntity.ok(operacaoRepository.findAll(page));

    }
}