package ao.ucan.authservice.config;

import ao.ucan.authservice.model.Usuario;
import ao.ucan.authservice.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RHSeeder {

    @Bean
    CommandLineRunner criarRH(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

         

           Usuario rh = usuarioRepository.findByUsername("rh01")
        .orElseGet(Usuario::new);

rh.setUsername("rh01");
rh.setPassword(passwordEncoder.encode("Rh@12345"));
rh.setRole("RH");
rh.setFuncionarioId("6ae72a55357ae26615ea16e3");
rh.setEstadoRegisto("CONCLUIDO");
rh.setAtivo(true);

usuarioRepository.save(rh);

System.out.println("Conta RH criada ou atualizada com sucesso!");
        };
    }
}