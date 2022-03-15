package com.desafioProject.Cliente.api.controller;

import com.desafioProject.Cliente.api.dto.request.ClienteDto;
import com.desafioProject.Cliente.api.dto.response.ClienteResponse;
import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.repository.ClienteRepository;
import com.desafioProject.Cliente.model.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/cliente")
@RestController
@Slf4j
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@RequestBody @Valid ClienteDto clienteDto) {
        ClienteResponse cliente =  clienteService.salvar(clienteDto);
        return ResponseEntity.created(null).body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> localizarId(@PathVariable("id") Long id) {
        ClienteDto cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscarCpf/")
    public ResponseEntity<ClienteDto> localizarCpf(@RequestParam(name = "cpf") String cpf){
        ClienteDto clienteRetorno = clienteService.localizarCpf(cpf);
        return ResponseEntity.ok(clienteRetorno);
    }

    @GetMapping("/buscarCnpj/")
    public ResponseEntity<ClienteDto> localizarCnpj(@RequestParam(name = "cnpj") String cnpj){
        ClienteDto clienteRetorno = clienteService.localizarCnpj(cnpj);
        return ResponseEntity.ok(clienteRetorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listar() {
        List<ClienteDto> listaClientes = clienteService.listar();
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/pagina/{numeroPagina}/{qtdePagina}")
    public ResponseEntity<Iterable<Cliente>> listaPaginada(@PathVariable int numeroPagina, @PathVariable int qtdePagina) {
        Pageable page = PageRequest.of(numeroPagina, qtdePagina);
        return ResponseEntity.ok(clienteRepository.findAll(page));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizar(@RequestBody ClienteDto clienteDto, @PathVariable Long id) {
        ClienteDto clienteDtoAtualizado = clienteService.atualizar(clienteDto, id);
        return ResponseEntity.ok(clienteDtoAtualizado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizar2(@RequestBody ClienteDto clienteDto, @PathVariable Long id) {
        ClienteDto clienteDtoAtualizado = clienteService.atualizar2(clienteDto, id);
        return ResponseEntity.ok(clienteDtoAtualizado);
    }
}