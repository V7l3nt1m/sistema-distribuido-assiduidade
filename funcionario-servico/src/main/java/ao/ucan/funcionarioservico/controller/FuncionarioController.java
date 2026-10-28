/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.funcionarioservico.controller;
import ao.ucan.funcionarioservico.model.Funcionario;
import ao.ucan.funcionarioservico.servico.FuncionarioServico;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author pradopc
 */
@RestController
@RequestMapping("/api/funcionarios")

public class FuncionarioController 
{
    private final FuncionarioServico funcionarioServico;
    
    public FuncionarioController(FuncionarioServico funcionarioServico)
    {
        this.funcionarioServico = funcionarioServico;
    }
    
    @PostMapping
    public ResponseEntity<Funcionario> criar (@RequestBody Funcionario funcionario)
    {
        Funcionario novoFuncionario = funcionarioServico.criar(funcionario);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }
    
    @GetMapping()
    public ResponseEntity<List<Funcionario>> listarTodos()
    {
        return ResponseEntity.ok(funcionarioServico.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable String id)
    {
        return funcionarioServico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable String id,@RequestBody Funcionario funcionario)
    {
        return funcionarioServico.atualizar(id, funcionario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id)
    {
        boolean eliminado = funcionarioServico.eliminar(id);
        
        if (! eliminado) return ResponseEntity.notFound().build();
        
        return ResponseEntity.noContent().build();
        
    }
}
