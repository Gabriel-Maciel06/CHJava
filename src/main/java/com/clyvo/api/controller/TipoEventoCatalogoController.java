package com.clyvo.api.controller;

import com.clyvo.api.model.TipoEventoCatalogo;
import com.clyvo.api.repository.TipoEventoCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-evento")
public class TipoEventoCatalogoController {

    @Autowired
    private TipoEventoCatalogoRepository repository;

    @GetMapping
    public ResponseEntity<Page<TipoEventoCatalogo>> listar(Pageable p) {
        return ResponseEntity.ok(repository.findAll(p));
    }

    @PostMapping
    public ResponseEntity<TipoEventoCatalogo> salvar(@RequestBody TipoEventoCatalogo tipo) {
        return ResponseEntity.status(201).body(repository.save(tipo));
    }
}
