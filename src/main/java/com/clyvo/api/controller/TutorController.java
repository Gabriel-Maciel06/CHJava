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
}
