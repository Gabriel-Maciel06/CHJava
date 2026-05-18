package com.clyvo.api.controller;

import com.clyvo.api.model.Pet;
import com.clyvo.api.model.Tutor;
import com.clyvo.api.repository.PetRepository;
import com.clyvo.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/{cpf}")
    public ResponseEntity<Tutor> buscarPerfil(@PathVariable String cpf) {
        return tutorRepository.findById(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{cpf}/pets")
    public ResponseEntity<List<Pet>> listarPetsDoTutor(@PathVariable String cpf) {
        List<Pet> pets = petRepository.findByTutorCpf(cpf);
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<Tutor> salvar(@RequestBody Tutor tutor) {
        return ResponseEntity.status(201).body(tutorRepository.save(tutor));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Tutor> atualizar(@PathVariable String cpf, @RequestBody Tutor tutorAtualizado) {
        return tutorRepository.findById(cpf).map(tutor -> {
            tutor.setNome(tutorAtualizado.getNome());
            tutor.setTelefone(tutorAtualizado.getTelefone());
            tutor.setEmail(tutorAtualizado.getEmail());
            tutor.setQtdPets(tutorAtualizado.getQtdPets());
            return ResponseEntity.ok(tutorRepository.save(tutor));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        tutorRepository.deleteById(cpf);
        return ResponseEntity.noContent().build();
    }
}
