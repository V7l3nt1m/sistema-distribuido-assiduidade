package ao.ucan.authservice.config;

import ao.ucan.authservice.model.Usuario;
import ao.ucan.authservice.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminIniciar {

    @Bean
    public CommandLineRunner criarAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!usuarioRepository.existsByUsername("admin")) {

                Usuario admin = new Usuario();

                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setFuncionarioId(null);
                admin.setAtivo(true);

                usuarioRepository.save(admin);

                System.out.println("Utilizador ADMIN criado com sucesso!");
            }
        };
    }
}