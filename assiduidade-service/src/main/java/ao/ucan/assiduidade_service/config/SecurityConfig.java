package ao.ucan.assiduidade_service.config;

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

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**"
    ).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/assiduidade")
                    .hasRole("RH")

                .requestMatchers(HttpMethod.GET, "/api/assiduidade")
                    .hasAnyRole("RH", "FINANCEIRO")

                .requestMatchers(HttpMethod.GET, "/api/assiduidade/**")
                    .hasAnyRole("RH", "FINANCEIRO", "USER")

                .requestMatchers(HttpMethod.PUT, "/api/assiduidade/**")
                    .hasRole("RH")

                .requestMatchers(HttpMethod.DELETE, "/api/assiduidade/**")
                    .hasRole("RH")

                .anyRequest().authenticated()
            )

            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }
}