package com.example.mercado.controller;

import com.example.mercado.exception.CouldNotCreateException;
import com.example.mercado.exception.CouldNotUpdateException;
import com.example.mercado.exception.InternalServerErrorException;
import com.example.mercado.exception.InvalidParamsException;
import com.example.mercado.model.Mercado;
import com.example.mercado.model.ValorMoeda;
import com.example.mercado.service.MercadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mercados")
@Slf4j
public class MercadoController {

    private MercadoService service;

    public MercadoController(MercadoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Mercado>> salvar(@RequestBody Mercado mercado) {
        return Mono.justOrEmpty(mercado)
                .switchIfEmpty(Mono.error(new InvalidParamsException("Parametros inválidos: verifique as informações enviadas")))
                .flatMap(m -> service.salvar(m))
                .switchIfEmpty(Mono.error(new CouldNotCreateException("Falha ao criar um mercado")))
                .map(atual -> ResponseEntity.ok().body(mercado));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Mercado>> atualizar(
            @RequestBody Mercado mercado,
            @PathVariable(value = "id") String id) {

        if (id.isEmpty()) {
            throw new InvalidParamsException("Id nao pode ser nulo");
        }

        return Mono.justOrEmpty(mercado)
                .switchIfEmpty(Mono.error(new InvalidParamsException("verifique as informações enviadas")))
                .flatMap(m -> service.atualizar(m, id))
                .switchIfEmpty(Mono.error(new CouldNotUpdateException("Falha ao criar um mercado")))
                .map(atual -> ResponseEntity.ok().body(mercado));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mercado>> buscarPorId(
            @PathVariable(value = "id") String id) {

        if (id.isEmpty()) {
            throw new InvalidParamsException("Id nao pode ser nulo");
        }

        return service.buscarPorId(id)
                .map(mercado -> ResponseEntity.ok().body(mercado))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(
                        e -> Mono.error(new InternalServerErrorException(e.getMessage())
                        ));
    }

    @GetMapping("/valormoeda")
    public Mono<ResponseEntity<ValorMoeda>> buscarValorMoeda(
            @RequestParam(value = "moeda") String moeda) {

        if (moeda.isEmpty()) {
            return Mono.error(new InvalidParamsException("Moeda nao pode ser nulo"));
        }

        return service.buscarValorMoeda(moeda)
                .map(valor -> ResponseEntity.ok().body(valor))
                .defaultIfEmpty(ResponseEntity.noContent().build())
                .onErrorResume(
                        e -> Mono.error(new InternalServerErrorException(e.getMessage())
                        ));
    }

    @GetMapping("/nomes")
    public Mono<ResponseEntity<Flux<Mercado>>> buscarPorNomes(
            @RequestParam(value = "nome") String nome) {

        if (nome.isEmpty()) {
            throw new InvalidParamsException("Nome nao pode ser nulo");
        }

        return service.buscarPorNomes(nome)
                .collectList()
                .map(mercados -> ResponseEntity.ok().body(Flux.fromIterable(mercados)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(
                        e -> Mono.error(new InternalServerErrorException(e.getMessage())
                        ));
    }

    @GetMapping()
    public Mono<ResponseEntity<Flux<Mercado>>> listarTodos() {
        return service.listarTodos()
                .collectList()
                .map(mercados -> ResponseEntity.ok().body(Flux.fromIterable(mercados)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(
                        e -> Mono.error(new InternalServerErrorException(e.getMessage())
                        ));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> remover(@PathVariable String id) {
        return service.remover(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(
                        e -> Mono.error(new InternalServerErrorException(e.getMessage())
                        ));
    }

}