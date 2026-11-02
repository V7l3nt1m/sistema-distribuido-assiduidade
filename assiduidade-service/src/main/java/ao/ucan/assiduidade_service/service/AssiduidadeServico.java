/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.assiduidade_service.service;

import ao.ucan.assiduidade_service.model.Assiduidade;
import ao.ucan.assiduidade_service.model.EstadoAssiduidade;
import ao.ucan.assiduidade_service.repository.AssuidadeRepository;
import org.springframework.stereotype.Service;
import ao.ucan.assiduidade_service.cliente.FuncionarioCliente;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

/**
 *
 * @author pradopc
 */
@Service
public class AssiduidadeServico 
{
    private final AssuidadeRepository assuidadeRepository;
    private final FuncionarioCliente funcionarioCliente;
    
    public AssiduidadeServico(AssuidadeRepository assuidadeRepository, FuncionarioCliente funcionarioCliente)
    {
        this.assuidadeRepository = assuidadeRepository;
        this.funcionarioCliente = funcionarioCliente;
    }
    
    //Registrar assiduidade
    
    public Assiduidade registrarAssiduidade(
        Assiduidade registo,
        String token) {

    if (registo.getFuncionarioId() == null ||
            registo.getFuncionarioId().isBlank()) {
        throw new IllegalArgumentException(
                "O funcionário é obrigatório."
        );
    }

    if (registo.getData() == null || registo.getEstado() == null) {
        throw new IllegalArgumentException(
                "A data e o estado da assiduidade são obrigatórios."
        );
    }

    // Confirmar se o funcionário existe no funcionario-servico
    boolean existe = funcionarioCliente.funcionarioExiste(
            registo.getFuncionarioId(),
            token
    );

    if (!existe) {
        throw new IllegalArgumentException(
                "O funcionário indicado não existe."
        );
    }

    // Impedir registos duplicados
    boolean jaExiste = assuidadeRepository.existsByFuncionarioIdAndData(
            registo.getFuncionarioId(),
            registo.getData()
    );

    if (jaExiste) {
        throw new IllegalArgumentException(
                "Já existe um registo de assiduidade para este funcionário nesta data."
        );
    }

    // Validar horas
    if ((registo.getEstado() == EstadoAssiduidade.PRESENCA ||
            registo.getEstado() == EstadoAssiduidade.ATRASO)
            && registo.getHoraEntrada() == null) {

        throw new IllegalArgumentException(
                "A hora de entrada é obrigatória para presença ou atraso."
        );
    }

    if (registo.getHoraEntrada() != null &&
            registo.getHoraSaida() != null &&
            !registo.getHoraSaida().isAfter(registo.getHoraEntrada())) {

        throw new IllegalArgumentException(
                "A hora de saída deve ser posterior à hora de entrada."
        );
    }

    return assuidadeRepository.save(registo);


}
    
    public List<Assiduidade> listarAll()
    {
        return assuidadeRepository.findAll();
    }
    
    public Optional<Assiduidade> buscarPorId(String id)
    {
        return assuidadeRepository.findById(id);
    }
    
    public List<Assiduidade> buscarPorFunc(String funcionarioId)
    {
        return assuidadeRepository.findByFuncionarioId(funcionarioId);
    }
    //assiduidade por periodo
    public List<Assiduidade> buscarPorPeriodo(String funcionarioId, LocalDate inicio, LocalDate fim)
    {
        if (inicio.isAfter(fim)) 
        {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final");
            
        }
        
        return assuidadeRepository.findByFuncionarioIdAndDataBetween(funcionarioId, inicio, fim);
    }
    
    // Atualizar registo
    public Optional<Assiduidade> atualizar(
        String id,
        Assiduidade novosDados) {

    return assuidadeRepository.findById(id).map(registo -> {

        if (novosDados.getData() == null ||
                novosDados.getEstado() == null) {
            throw new IllegalArgumentException(
                    "A data e o estado são obrigatórios."
            );
        }

        if ((novosDados.getEstado() == EstadoAssiduidade.PRESENCA ||
                novosDados.getEstado() == EstadoAssiduidade.ATRASO)
                && novosDados.getHoraEntrada() == null) {

            throw new IllegalArgumentException(
                    "A hora de entrada é obrigatória."
            );
        }

        if (novosDados.getHoraEntrada() != null &&
                novosDados.getHoraSaida() != null &&
                !novosDados.getHoraSaida()
                        .isAfter(novosDados.getHoraEntrada())) {

            throw new IllegalArgumentException(
                    "A hora de saída deve ser posterior à entrada."
            );
        }

        registo.setData(novosDados.getData());
        registo.setHoraEntrada(novosDados.getHoraEntrada());
        registo.setHoraSaida(novosDados.getHoraSaida());
        registo.setEstado(novosDados.getEstado());
        registo.setObservacao(novosDados.getObservacao());

        return assuidadeRepository.save(registo);
    });
}

    // Eliminar registo
    public boolean eliminar(String id) {

        if (!assuidadeRepository.existsById(id)) {
            return false;
        }

        assuidadeRepository.deleteById(id);
        return true;
    }
    
    
    
}
