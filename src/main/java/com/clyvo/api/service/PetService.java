package com.clyvo.api.service;

import com.clyvo.api.dto.PetDTO;
import com.clyvo.api.exception.RecursoNaoEncontradoException;
import com.clyvo.api.model.Pet;
import com.clyvo.api.repository.PetRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    @Cacheable("petInsights")
    public String calcularInsightIA(Pet pet) {
        int idade = Period.between(pet.getDataNascimento(), LocalDate.now()).getYears();
        String raca = (pet.getRaca() != null) ? pet.getRaca().getNome() : "Desconhecida";

        // Lógica Preditiva Dinâmica
        String propensao = (pet.getRaca() != null) ? pet.getRaca().getPropensaoDoenca() : null;
        String cuidados = (pet.getRaca() != null) ? pet.getRaca().getCuidadosEspeciais() : null;
        
        if (idade > 7 && propensao != null && !propensao.isEmpty()) {
            return "Alerta de Idade: Risco de " + propensao + ". Recomendamos exames preventivos.";
        } else if (cuidados != null && !cuidados.isEmpty()) {
            return "Cuidado Específico da Raça (" + raca + "): " + cuidados;
        }
        
        return "Saúde estável. Continue com o plano de longevidade.";
    }

    public Page<PetDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToDTO);
    }

    public PetDTO buscarPorId(Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pet não encontrado"));
        
        PetDTO dto = mapToDTO(pet);
        // Não vamos forçar IA no DTO aqui para não quebrar a estrutura, ou podemos se houver campo
        return dto;
    }

    public PetDTO salvar(PetDTO dto) {
        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setDataNascimento(dto.getDataNascimento());
        pet.setPeso(dto.getPeso());
        // Tutor precisaria ser buscado no banco para associar corretamente, mas para CP simplificado setamos null ou buscamos
        
        Pet salvo = repository.save(pet);
        return mapToDTO(salvo);
    }

    public PetDTO atualizar(Long id, PetDTO dto) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pet não encontrado"));
        
        pet.setNome(dto.getNome());
        pet.setDataNascimento(dto.getDataNascimento());
        pet.setPeso(dto.getPeso());
        
        Pet salvo = repository.save(pet);
        return mapToDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pet não encontrado");
        }
        repository.deleteById(id);
    }

    private PetDTO mapToDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setNome(pet.getNome());
        dto.setDataNascimento(pet.getDataNascimento());
        dto.setPeso(pet.getPeso());
        // Assumindo que Tutor pode não estar fetchado
        if (pet.getTutor() != null) {
            dto.setTutorCpf(pet.getTutor().getCpf());
        }
        return dto;
    }
}
