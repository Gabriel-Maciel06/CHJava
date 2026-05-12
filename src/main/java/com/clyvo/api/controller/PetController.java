package com.clyvo.api.controller;

import com.clyvo.api.model.Pet;
import com.clyvo.api.repository.PetRepository;
import com.clyvo.api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @Autowired
    private PetService service;

    @GetMapping
    public ResponseEntity<Page<Pet>> listar(@PageableDefault(size = 5) Pageable paginacao) {
        return ResponseEntity.ok(repository.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public EntityModel<Pet> buscarPorId(@PathVariable Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        // Adiciona insight da IA dinamicamente
        pet.setStatusLongevidade(service.calcularInsightIA(pet));

        return EntityModel.of(pet,
                linkTo(methodOn(PetController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(PetController.class).listar(null)).withRel("lista-pets"));
    }
}
