package com.clyvo.api.service;

import com.clyvo.api.model.Clinica;
import com.clyvo.api.model.ContextoLocalizacao;
import com.clyvo.api.model.ServicoClinica;
import com.clyvo.api.repository.ContextoLocalizacaoRepository;
import com.clyvo.api.repository.ServicoClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchPreditivoService {

    @Autowired
    private ContextoLocalizacaoRepository localizacaoRepository;

    @Autowired
    private ServicoClinicaRepository servicoClinicaRepository;

    public List<Clinica> buscarClinicasProximasParaServico(String cpfTutor, Long idTipoServico) {
        // 1. Busca a última localização do tutor
        ContextoLocalizacao localizacao = localizacaoRepository.findTopByTutorCpfOrderByDataHoraCapturaDesc(cpfTutor)
                .orElseThrow(() -> new RuntimeException("Localização do tutor não encontrada."));

        // 2. Busca os serviços disponíveis para o tipo solicitado
        List<ServicoClinica> servicos = servicoClinicaRepository.findClinicasByTipoServico(idTipoServico);

        // 3. (Lógica Preditiva) Filtra clínicas na mesma cidade/região da última localização detectada.
        // *Em uma aplicação em larga escala com PostGIS/Oracle Spatial, usaríamos ST_Distance para calcular o Haversine.*
        return servicos.stream()
                .map(ServicoClinica::getClinica)
                .filter(clinica -> clinica.getCidade() != null && clinica.getCidade().equalsIgnoreCase(localizacao.getCidadeDetectada()))
                .collect(Collectors.toList());
    }
}
