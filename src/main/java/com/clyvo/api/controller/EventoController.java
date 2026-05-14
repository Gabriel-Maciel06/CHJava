package com.clyvo.api.controller;
import com.clyvo.api.model.Evento;
import com.clyvo.api.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/eventos")
public class EventoController {
    @Autowired private EventoRepository repository;
    @GetMapping public ResponseEntity<Page<Evento>> listar(Pageable p) { return ResponseEntity.ok(repository.findAll(p)); }
    @PostMapping public ResponseEntity<Evento> salvar(@RequestBody Evento e) { return ResponseEntity.ok(repository.save(e)); }
}
