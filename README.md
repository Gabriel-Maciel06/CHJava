<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
</div>

<h1 align="center">🐾 Clyvo Vet - API Preditiva de Longevidade Pet</h1>

<p align="center">
  <strong>Projeto do 1º Challenge Sprint - Disciplina de Java Advanced (FIAP)</strong>
</p>

## 📖 Sobre o Projeto
O **Clyvo Vet** é uma plataforma inovadora baseada em análise de dados e Inteligência Artificial para medicina veterinária preventiva. Esta API RESTful fornece os recursos centrais para gerenciar Tutores, Pets, Clínicas e Tratamentos, além de gerar predições de saúde com base nas características raciais e de longevidade dos animais.

## 🚀 Tecnologias Utilizadas
- **Java 21** - Versão LTS mais recente com suporte avançado a Records e Virtual Threads.
- **Spring Boot 3.2+** - Framework base da aplicação.
- **Spring Data JPA & Hibernate** - Mapeamento Objeto-Relacional (ORM) estruturado.
- **Oracle 23c Free (Docker)** - Banco de dados de altíssimo desempenho com suporte nativo à arquitetura ARM64 (Apple Silicon).
- **Lombok** - Redução drástica de boilerplate code.
- **Jakarta Bean Validation** - Garantia de integridade de dados na entrada da API.
- **Spring HATEOAS** - Maturidade REST Nível 3 (Hypermedia as the Engine of Application State).

## 📊 Modelo Entidade Relacionamento (MER)
```mermaid
erDiagram
    TUTOR ||--o{ PET : "cuida de"
    TUTOR ||--o{ EVENTO : "solicita"
    TUTOR ||--o{ CONTEXTO_LOCALIZACAO : "possui"
    RACA ||--o{ PET : "define"
    PET ||--o{ EVENTO : "participa"
    PET ||--o{ TRATAMENTO : "recebe"
    MEDICO_ESPECIALISTA ||--o{ EVENTO : "realiza"
    CLINICA ||--o{ EVENTO : "sedia"
    CLINICA ||--o{ SERVICO_CLINICA : "oferece"
    TIPO_EVENTO_CATALOGO ||--o{ SERVICO_CLINICA : "tipifica"
    EVENTO ||--|| HISTORICO_CLINICO : "gera"
    
    TUTOR {
        String cpf PK
        String nome
        String telefone
        String email
        Integer qtd_pets
    }
    CONTEXTO_LOCALIZACAO {
        Long id PK
        String cpf_tutor FK
        Double latitude_atual
        Double longitude_atual
        Timestamp data_hora_captura
        String cidade_detectada
    }
    CLINICA {
        Long id PK
        String nomeCnpj
        String telefone
        Double latitude
        Double longitude
        String bairro
        String cidade
        String estado
        Boolean atendimento_24h
    }
    TIPO_EVENTO_CATALOGO {
        Long id PK
        String descricao
        String categoria
    }
    SERVICO_CLINICA {
        Long id PK
        Long id_clinica FK
        Long id_tipo_catalogo FK
        Boolean disponivel
    }
    PET {
        Long id PK
        String nome
        LocalDate dataNascimento
        Double peso
        String statusLongevidade
        Long raca_id FK
        String tutor_cpf FK
    }
    MEDICO_ESPECIALISTA {
        Long id PK
        String nome
        String especialidade
    }
    EVENTO {
        Long id PK
        String tipo
        Long id_pet FK
        String id_tutor FK
        Long id_medico FK
        Long id_clinica FK
    }
    HISTORICO_CLINICO {
        Long id PK
        Long id_evento FK
        LocalDate dataEvento
        LocalDate dataVencimento
        String status
        String observacoesIa
    }
    TRATAMENTO {
        Long id PK
        Long id_pet FK
        String nomeMedicamento
        String frequencia
        LocalDate dataInicio
        LocalDate dataFinal
    }
```

## 🏗️ Arquitetura e Padrões (Clean Architecture)
A aplicação adere estritamente às melhores práticas de Engenharia de Software exigidas pela FIAP:
1. **Padrão DTO:** Entidades de banco (`@Entity`) não são expostas na web. Toda a comunicação ocorre via objetos de transferência de dados (DTOs) estritamente validados.
2. **Injeção de Dependências por Construtor:** Abandono do problemático `@Autowired` em propriedades em favor da injeção explícita no construtor via `@RequiredArgsConstructor`.
3. **Controller-Service-Repository:** Lógica de negócios isolada 100% na camada Service, mantendo os Controllers limpos para tratar apenas requisições HTTP e roteamento.
4. **Tratamento Global de Exceções:** Configuração com `@RestControllerAdvice` para evitar stack traces vazando para o usuário e formatar erros RESTful (ex: `404 Not Found`, `400 Bad Request`).

## 📦 Como Rodar a Aplicação Localmente

### Pré-requisitos
- Docker e Docker Compose instalados.
- JDK 21 instalado e configurado no seu PATH.
- Maven 3.8+ instalado.

### Passo 1: Subir a Infraestrutura de Banco de Dados
A aplicação depende de um contêiner **Oracle 23c Free** localmente para persistência real. 
```bash
# Na raiz do projeto DevOps ou diretório configurado
docker compose up -d
```

### Passo 2: Inicializar a API
Com o banco rodando, inicie o Spring Boot:
```bash
mvn spring-boot:run
```
A API ficará disponível em: `http://localhost:8080/`

## 📡 Endpoints Principais (API)
A documentação completa (Collection) está na pasta `/Postman` na raiz do repositório. Principais rotas:

### 🧑‍💼 Tutores (`/api/tutores`)
- `POST` - Cadastrar novo Tutor.
- `GET /{cpf}` - Buscar detalhes do Tutor.
- `PUT /{cpf}` - Atualizar informações de contato.
- `DELETE /{cpf}` - Excluir Tutor.

### 🐕 Pets (`/api/pets`)
- `POST` - Registrar um novo Pet vinculado a um Tutor.
- `GET` - Listar todos os Pets (com Suporte a Paginação `?size=5&page=0`).
- `GET /{id}` - Buscar Pet detalhado com **HATEOAS** e Insights de Longevidade IA.

## 👥 Integrantes do Grupo
- **Vitória Rodrigues Martins** - RM565160
- **Augusto Bonomo Junior** - RM565155
- **Thomas Fontes** - RM562254
- **Gabriel Maciel** - RM562795
- **Matheus Pereira Molina** - RM563399
