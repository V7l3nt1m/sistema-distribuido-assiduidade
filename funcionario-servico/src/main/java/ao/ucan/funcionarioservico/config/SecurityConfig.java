package ao.ucan.funcionarioservico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/actuator/health").permitAll()

                // Consultar funcionários: ADMIN ou USER
                .requestMatchers(HttpMethod.GET, "/api/funcionarios")
                    .hasRole("ADMIN")

                // Criar funcionários: apenas ADMIN
                .requestMatchers(HttpMethod.POST, "/api/funcionarios")
                    .hasRole("ADMIN")

                // Atualizar funcionários: apenas ADMIN
                .requestMatchers(HttpMethod.PUT, "/api/funcionarios/**")
                    .hasRole("ADMIN")

                // Eliminar funcionários: apenas ADMIN
                .requestMatchers(HttpMethod.DELETE, "/api/funcionarios/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            ) 
            .oauth2ResourceServer(oath2 -> oath2.jwt(Customizer.withDefaults())) //assim ele vai procurar o cabeçalho autorization bearer   
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}