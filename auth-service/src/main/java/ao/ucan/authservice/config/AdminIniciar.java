package ao.ucan.authservice.config;

import ao.ucan.authservice.model.Usuario;
import ao.ucan.authservice.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AdminIniciar {

    @Bean
    public CommandLineRunner criarAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder, MongoTemplate mongoTemplate) {

        return args -> {
            
            System.out.println("BASE DE DADOS UTILIZADA: "
        + mongoTemplate.getDb().getName());

            System.out.println(">>> ADMIN INICIAR: RUNNER EXECUTADO <<<");

            boolean existe = usuarioRepository.existsByUsername("admin");

            System.out.println(">>> ADMIN EXISTE: " + existe);

            if (!existe) {

                Usuario admin = new Usuario();

                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setFuncionarioId(null);
                admin.setEstadoRegisto("CONCLUIDO");
                admin.setAtivo(true);

                usuarioRepository.save(admin);

                System.out.println(">>> UTILIZADOR ADMIN CRIADO <<<");
            }
        };
    }
}
