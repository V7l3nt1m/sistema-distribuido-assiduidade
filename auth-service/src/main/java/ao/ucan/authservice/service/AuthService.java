    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.service;
import org.springframework.stereotype.Service;
import ao.ucan.authservice.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import ao.ucan.authservice.model.Usuario;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import java.time.Instant;
import java.util.List;

import java.util.Optional;


/**
 *
 * @author pradopc
 */

@Service
public class AuthService 
{ 
    private final UsuarioRepository usuarioRepository; //permie consultar users no banco de dados
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    
    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,JwtEncoder jwtEncoder)
    {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }
    
   
    public Usuario autenticar(String username, String senha)
    {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        
        if (usuarioOptional.isEmpty()) 
        {
            throw new RuntimeException("Credencias invalidas");
        }
        
        Usuario user = usuarioOptional.get();
        
        if (!user.isAtivo()) 
        {
            throw new RuntimeException("Conta desativada");
        }
        
        if(!passwordEncoder.matches(senha, user.getPassword()))
        {
             throw new RuntimeException("Credenciais invalidas");
        }
        
        return user;
    }
    
        public String gerarToken(Usuario usuario) 
        {

        Instant agora = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(3600))
                .subject(usuario.getUsername())
                .claim("role", usuario.getRole());

        if (usuario.getFuncionarioId() != null) {
            claimsBuilder.claim("funcionarioId", usuario.getFuncionarioId());
        }

        JwtClaimsSet claims = claimsBuilder.build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();
    }
    
    public String login(String username, String password)
    {
        Usuario usuario = autenticar(username, password);
        
        return gerarToken(usuario);
    }
    
    public Usuario registarUsuario(
        String username,
        String password,
        String role,
        String funcionarioId) {

        // Verificar se o username já existe
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já está em utilização");
        }

        // Validar a role
        if (!role.equals("ADMIN") &&
            !role.equals("RH") &&
            !role.equals("USER") &&
            !role.equals("FINANCEIRO")) {

            throw new RuntimeException("Role inválida");
        }

        // Criar novo utilizador
        Usuario novoUsuario = new Usuario();

        novoUsuario.setUsername(username);
        novoUsuario.setPassword(passwordEncoder.encode(password));
        novoUsuario.setRole(role);
        novoUsuario.setFuncionarioId(funcionarioId);
        novoUsuario.setAtivo(true);

        // Guardar no MongoDB
        return usuarioRepository.save(novoUsuario);
    }
    
    public List<Usuario> listarUsuarios()
    {
        return usuarioRepository.findAll();
    }
    
    public boolean desativarUsuario(String id) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

    if (usuarioOptional.isEmpty()) {
        return false;
    }

    Usuario usuario = usuarioOptional.get();
    usuario.setAtivo(false);

    usuarioRepository.save(usuario);

    return true;
}
    
    public boolean ativarUsuario(String id) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

    if (usuarioOptional.isEmpty()) {
        return false;
    }

    Usuario usuario = usuarioOptional.get();
    usuario.setAtivo(true);

    usuarioRepository.save(usuario);

    return true;
}
    
}
