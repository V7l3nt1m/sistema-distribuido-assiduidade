/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.assiduidade_service.controller;
import ao.ucan.assiduidade_service.model.Assiduidade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import ao.ucan.assiduidade_service.service.AssiduidadeServico;

import java.util.List;
import java.time.LocalDate;
/**
 *
 * @author pradopc
 */
@RestController
@RequestMapping("/api/assiduidade")
public class RegistoAssiduidadeController 
{
    private final AssiduidadeServico assiduidadeServico;
    
    public RegistoAssiduidadeController(AssiduidadeServico assiduidadeServico)
    {
        this.assiduidadeServico = assiduidadeServico;
    }
    
    @PostMapping
    public ResponseEntity<Assiduidade> registrar(@RequestBody Assiduidade assiduidade, JwtAuthenticationToken auth)
    {
        String token = auth.getToken().getTokenValue();
        
        Assiduidade novo = assiduidadeServico.registrarAssiduidade(assiduidade,token);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
    
    @GetMapping()
    public ResponseEntity<List<Assiduidade>> listarTodos()
    {
        return ResponseEntity.ok(assiduidadeServico.listarAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Assiduidade> buscarPorId(@PathVariable String id, JwtAuthenticationToken auth  )
    {
        return assiduidadeServico.buscarPorId(id)
                .filter(registo -> podeConsultar(auth, registo.getFuncionarioId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<?> buscarPorFuncionario(@PathVariable String funcionarioId, 
            JwtAuthenticationToken auth)
    {
        if (!podeConsultar(auth, funcionarioId)) 
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(assiduidadeServico.buscarPorFunc(funcionarioId));
    }
    
    @GetMapping("/funcionario/{funcionarioId}/periodo")
    public ResponseEntity<?> buscarPorPeriodo(@PathVariable String funcionarioId,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            JwtAuthenticationToken auth)
    {
        if (!podeConsultar(auth, funcionarioId)) 
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(assiduidadeServico.buscarPorPeriodo(funcionarioId, inicio, fim));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Assiduidade> actualizar(@PathVariable String id, @RequestBody Assiduidade assiduidade)
    {
        return assiduidadeServico.atualizar(id, assiduidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {

        boolean eliminado = assiduidadeServico.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    
    private boolean podeConsultar(
            JwtAuthenticationToken authentication,
            String funcionarioId) {

        String role = authentication.getToken()
                .getClaimAsString("role");

        if ("RH".equals(role) || "FINANCEIRO".equals(role)) 
        {
            return true;
        }

        if ("USER".equals(role)) {
            String funcionarioIdToken = authentication.getToken()
                    .getClaimAsString("funcionarioId");

            return funcionarioId.equals(funcionarioIdToken);
        }

        return false;
    }
}
