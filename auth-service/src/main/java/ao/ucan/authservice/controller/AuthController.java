/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.controller;
import ao.ucan.authservice.dto.LoginRequest;
import ao.ucan.authservice.dto.LoginResposta;
import ao.ucan.authservice.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ao.ucan.authservice.dto.RegistroUserRequest;
import ao.ucan.authservice.model.Usuario;
import ao.ucan.authservice.dto.UserResposta;
import java.util.List;
/**
 *
 * @author pradopc
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
    private final AuthService authservice;
    
    public AuthController(AuthService authservice)
    {
        this.authservice = authservice;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResposta> login(
            @RequestBody LoginRequest requisicao
    )
    {
        String token = authservice.login(requisicao.getUsername(), requisicao.getPassword());
        
        LoginResposta resposta = new LoginResposta(token,"Bearer",3600);
        
        return ResponseEntity.ok(resposta);
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResposta> registar(
        @RequestBody RegistroUserRequest requisicao) {

    Usuario novoUsuario = authservice.registarUsuario(
            requisicao.getUsername(),
            requisicao.getPassword(),
            requisicao.getRole(),
            requisicao.getFuncionarioId()
    );

    UserResposta resposta = new UserResposta(
            novoUsuario.getId(),
            novoUsuario.getUsername(),
            novoUsuario.getRole(),
            novoUsuario.getFuncionarioId(),
            novoUsuario.isAtivo()
    );

    return ResponseEntity.status(201).body(resposta);
}
    
    @GetMapping("/users")
    public ResponseEntity<List<UserResposta>> listarUsuarios() {

    List<UserResposta> usuarios = authservice.listarUsuarios()
            .stream()
            .map(usuario -> new UserResposta(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getRole(),
                    usuario.getFuncionarioId(),
                    usuario.isAtivo()
            ))
            .toList();

    return ResponseEntity.ok(usuarios);
}
    
    @PatchMapping("/users/{id}/deactivate")
    public ResponseEntity<Void> desativarUsuario(@PathVariable String id) {

        boolean desativado = authservice.desativarUsuario(id);

        if (!desativado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/users/{id}/activate")
    public ResponseEntity<Void> ativarUsuario(@PathVariable String id) {

        boolean ativado = authservice.ativarUsuario(id);

        if (!ativado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/admin")
    public ResponseEntity<String> testarAdmin() {
        return ResponseEntity.ok("Acesso autorizado: ADMIN");
    }
}
