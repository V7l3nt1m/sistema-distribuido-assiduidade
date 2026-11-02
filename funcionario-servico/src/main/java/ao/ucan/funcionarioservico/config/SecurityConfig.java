package ao.ucan.funcionarioservico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Configuration
public class SecurityConfig {
    
    @Bean
public JwtAuthenticationConverter jwtAuthenticationConverter() {

    JwtAuthenticationConverter converter =
            new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(jwt ->
            List.of(
                new SimpleGrantedAuthority(
                    "ROLE_" + jwt.getClaimAsString("role")
                )
            )
    );

    return converter;
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**"
    ).permitAll()
            .requestMatchers("/actuator/health").permitAll()

            // Listar funcionários: RH e FINANCEIRO
            .requestMatchers(HttpMethod.GET, "/api/funcionarios")
                .hasAnyRole("RH", "FINANCEIRO")

            // Consultar por ID: RH, FINANCEIRO e USER
            .requestMatchers(HttpMethod.GET, "/api/funcionarios/**")
                .hasAnyRole("RH", "FINANCEIRO", "USER")

            // Criar funcionários: RH
            .requestMatchers(HttpMethod.POST, "/api/funcionarios")
                .hasRole("RH")

            // Actualizar funcionários: RH
            .requestMatchers(HttpMethod.PUT, "/api/funcionarios/**")
                .hasRole("RH")

            // Eliminar funcionários: RH
            .requestMatchers(HttpMethod.DELETE, "/api/funcionarios/**")
                .hasRole("RH")

            .anyRequest().authenticated()
        )
            .oauth2ResourceServer(oauth2 -> oauth2
    .jwt(jwt -> jwt
        .jwtAuthenticationConverter(jwtAuthenticationConverter())
    )
) //assim ele vai procurar o cabeçalho autorization bearer   
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}