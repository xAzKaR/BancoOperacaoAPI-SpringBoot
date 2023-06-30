package com.desafioproject.cliente.controller;

import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.repository.OperacaoRepository;
import com.desafioproject.cliente.model.service.OperacaoService;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@RequestMapping("/api/operacao")
@RestController
@Slf4j
public class OperacaoController {

    private final OperacaoService operacaoService;
    private final OperacaoRepository operacaoRepository;

    @PostMapping("/depositar")
    public ResponseEntity<OperacaoDepositoResponse> depositar(@RequestBody @Valid OperacaoDto operacaoDto) throws OperacaoNaoCompletadaException {
        OperacaoDepositoResponse depositar = operacaoService.depositar(operacaoDto);
        return ResponseEntity.ok(depositar);
    }

    @PostMapping("/sacar")
    public ResponseEntity<OperacaoSaqueResponse> sacar(@RequestBody @Valid OperacaoDto operacaoDto) throws ExecutionException, InterruptedException, TimeoutException {
        OperacaoSaqueResponse sacar = operacaoService.sacar(operacaoDto);
        return ResponseEntity.ok(sacar);
    }

    @PostMapping("/transferir")
    public ResponseEntity<OperacaoTransfResponse> transferir(@RequestBody @Valid OperacaoDto operacaoDto) {
        OperacaoTransfResponse transferir = operacaoService.transferir(operacaoDto);
        return ResponseEntity.ok(transferir);
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