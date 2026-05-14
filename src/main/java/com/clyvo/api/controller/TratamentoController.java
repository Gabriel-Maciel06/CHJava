package com.clyvo.api.controller;
import com.clyvo.api.model.Tratamento;
import com.clyvo.api.repository.TratamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/tratamentos")
public class TratamentoController {
    @Autowired private TratamentoRepository repository;
    @PostMapping public ResponseEntity<Tratamento> salvar(@RequestBody Tratamento t) { return ResponseEntity.ok(repository.save(t)); }
}
