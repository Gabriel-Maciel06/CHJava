package com.clyvo.api.controller;
import com.clyvo.api.model.Clinica;
import com.clyvo.api.repository.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/clinicas")
public class ClinicaController {
    @Autowired private ClinicaRepository repository;
    @GetMapping public ResponseEntity<Page<Clinica>> listar(Pageable p) { return ResponseEntity.ok(repository.findAll(p)); }
    @PostMapping public ResponseEntity<Clinica> salvar(@RequestBody Clinica c) { return ResponseEntity.ok(repository.save(c)); }
    
    @PutMapping("/{id}")
    public ResponseEntity<Clinica> atualizar(@PathVariable Long id, @RequestBody Clinica c) {
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        c.setId(id);
        return ResponseEntity.ok(repository.save(c));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
