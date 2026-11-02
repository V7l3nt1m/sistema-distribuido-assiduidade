/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 *
 * @author pradopc
 */
@Configuration
public class ConfiguracaoSeguranca //classe para usar o algoritmo bcrypt e encritpar as senhas
{
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    
    public ConfiguracaoSeguranca(JwtAuthenticationConverter jwtAuthenticationConverter)
    {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth

            .requestMatchers("/api/auth/login").permitAll()
            .requestMatchers("/actuator/health").permitAll()

            // ADMIN apenas            
            .requestMatchers(HttpMethod.POST, "/api/auth/register").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/auth/users").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/auth/users/*/activate").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/auth/users/*/deactivate").hasRole("ADMIN")

            // RH: apenas
            .requestMatchers(HttpMethod.PATCH, "/api/auth/users/*/associarfunc").hasRole("RH")

            // endpoint de teste
            .requestMatchers("/api/auth/admin").hasRole("ADMIN")
            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**"
            ).permitAll()

            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
            )
        ); //permite ao spring validar os jwt
        
        return http.build();
    }
}
