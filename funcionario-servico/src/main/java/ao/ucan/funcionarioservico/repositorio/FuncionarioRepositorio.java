/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ao.ucan.funcionarioservico.repositorio;
import ao.ucan.funcionarioservico.model.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author pradopc
 */
public interface FuncionarioRepositorio extends MongoRepository<Funcionario, String> //isso é um json {"tipo de objecto":"id do user"}
{
    
}
