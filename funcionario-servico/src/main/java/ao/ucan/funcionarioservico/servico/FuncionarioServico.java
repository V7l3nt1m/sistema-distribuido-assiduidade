/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.funcionarioservico.servico;

import ao.ucan.funcionarioservico.model.Funcionario;
import ao.ucan.funcionarioservico.repositorio.FuncionarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

/**
 *
 * @author pradopc
 */
@Service
public class FuncionarioServico 
{
    private final FuncionarioRepositorio funcionarioRepositorio;
    
    public FuncionarioServico(FuncionarioRepositorio funcionarioRepositorio)
    {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }
    
    public Funcionario criar(Funcionario funcionario)
    {
        validarFuncionario(funcionario);
        return funcionarioRepositorio.save(funcionario);
    }
    
    public List<Funcionario> listarTodos()
    {
        return funcionarioRepositorio.findAll();
    }
    
    public Optional<Funcionario> buscarPorId(String id)
    {
       return funcionarioRepositorio.findById(id);
    }
    
    public Optional<Funcionario> atualizar(String id,Funcionario dadosAtualizados)
    {
        validarFuncionario(dadosAtualizados);
        
        return funcionarioRepositorio.findById(id)
                .map(funcionarioExistente -> {
                     funcionarioExistente.setNome(dadosAtualizados.getNome());
                     funcionarioExistente.setEmail(dadosAtualizados.getEmail());
                     funcionarioExistente.setCargo(dadosAtualizados.getCargo());
                     funcionarioExistente.setSalarioBase(dadosAtualizados.getSalarioBase());
                     
                     return funcionarioRepositorio.save(funcionarioExistente);
                });
    }
    
    public boolean eliminar(String id)
    {
        if(!funcionarioRepositorio.existsById(id)) return false;
        
        funcionarioRepositorio.deleteById(id);
        return true;
    }
    
    private void validarFuncionario(Funcionario funcionario)
    {
        if(funcionario == null) throw new IllegalArgumentException("Os dados sao obrigatorios");
        
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }

        if (!funcionario.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("O formato do email é inválido.");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new IllegalArgumentException("O cargo é obrigatório.");
        }
        
        BigDecimal salario = funcionario.getSalarioBase();

if (salario == null || salario.compareTo(BigDecimal.ZERO) < 0) {
    throw new IllegalArgumentException(
            "O salário base não pode ser nulo ou negativo."
    );
}
            
    }
    
        
    
}
