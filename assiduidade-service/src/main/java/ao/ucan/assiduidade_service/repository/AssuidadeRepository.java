/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.assiduidade_service.repository;
import ao.ucan.assiduidade_service.model.Assiduidade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author pradopc
 */
public interface AssuidadeRepository extends MongoRepository<Assiduidade, String>
{
    List<Assiduidade> findByFuncionarioId(String funcionarioId);
    
    List<Assiduidade> findByFuncionarioIdAndDataBetween(
            String funcionarioId,
            LocalDate dataInicio,
            LocalDate dataFim
    );
    
    Boolean existsByFuncionarioIdAndData(String funcionarioId, LocalDate data);
    
    List<Assiduidade> findByData(LocalDate date);
}
