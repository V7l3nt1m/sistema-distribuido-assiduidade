/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //usa sempre jwt em cada requisicao
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/login").permitAll() //permiteall permite realizar login sem possuir token
                    .requestMatchers("/actuator/health").permitAll()
                    .requestMatchers("/api/auth/admin").hasRole("ADMIN")
                    .requestMatchers("/api/auth/register").hasRole("ADMIN")
                    .requestMatchers("/api/auth/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated() //exige autenticacao
                )
                .oauth2ResourceServer(oauth2 -> oauth2
    .jwt(jwt -> jwt
        .jwtAuthenticationConverter(jwtAuthenticationConverter)
    )
); //permite ao spring validar os jwt
        
        return http.build();
    }
}
