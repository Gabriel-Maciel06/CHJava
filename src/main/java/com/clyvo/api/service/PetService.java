package com.clyvo.api.service;

import com.clyvo.api.model.Pet;
import com.clyvo.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public String calcularInsightIA(Pet pet) {
        int idade = Period.between(pet.getDataNascimento(), LocalDate.now()).getYears();
        String raca = pet.getRaca().getNome();

        // Lógica Preditiva (Simulação de IA para o Challenge)
        if (idade > 7 && raca.equalsIgnoreCase("Golden Retriever")) {
            return "Alerta: Idade crítica para exames oncológicos preventivos.";
        } else if (raca.equalsIgnoreCase("Bulldog Francês")) {
            return "Cuidado: Atenção redobrada com a respiração em dias quentes.";
        }
        
        return "Saúde estável. Continue com o plano de longevidade.";
    }
}
