package com.clyvo.api.controller;
import com.clyvo.api.model.MedicoEspecialista;
import com.clyvo.api.repository.MedicoEspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/especialistas")
public class MedicoEspecialistaController {
    @Autowired private MedicoEspecialistaRepository repository;
    @GetMapping public ResponseEntity<Page<MedicoEspecialista>> listar(Pageable p) { return ResponseEntity.ok(repository.findAll(p)); }
    @PostMapping public ResponseEntity<MedicoEspecialista> salvar(@RequestBody MedicoEspecialista m) { return ResponseEntity.ok(repository.save(m)); }
}
