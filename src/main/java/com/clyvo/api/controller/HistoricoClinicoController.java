package com.clyvo.api.controller;
import com.clyvo.api.model.HistoricoClinico;
import com.clyvo.api.repository.HistoricoClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/historico")
public class HistoricoClinicoController {
    @Autowired private HistoricoClinicoRepository repository;
    @GetMapping("/{id}") public ResponseEntity<HistoricoClinico> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<HistoricoClinico> salvar(@RequestBody HistoricoClinico h) { return ResponseEntity.ok(repository.save(h)); }
}
