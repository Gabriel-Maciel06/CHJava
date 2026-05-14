package com.clyvo.api.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "T_CLINICA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Clinica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCnpj;
    private String telefone;
}
