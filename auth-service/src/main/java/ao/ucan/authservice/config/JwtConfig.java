/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.jwt.JwtDecoder; //vai decodificar o jwt
import org.springframework.security.oauth2.jwt.JwtEncoder; //vai assinar o jwt
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder; //implementar o jwt
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.RSAKey; //permite trabalhar com tokens, usada pelo spring security
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

import java.security.interfaces.RSAPrivateKey; //trabalhar com chaves privada
import java.security.interfaces.RSAPublicKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;



/**
 *
 * @author pradopc
 */
@Configuration //marca que é um arquivo de configuração
public class JwtConfig 
{
    private static final String CHAVE_PRIVADA_CAMINHO = "src/main/resources/keys/private.pem";
    
    private static final String CHAVE_PUBLICA_CAMINHO = "src/main/resources/keys/public.pem";
    
   
    @Bean //outras classes poderao usar 
    public RSAKey chaveRsa() throws Exception //carrega e retorna as chaves rsa
    {
        String conteudoDaKeyPrivada = Files.readString(Paths.get(CHAVE_PRIVADA_CAMINHO))
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "") //remove os cabeçalhos e rodape para pegar o conteudo apenas
                .replaceAll("\\s", "");
        
        
        String conteudoDaKeyPublica = Files.readString(Paths.get(CHAVE_PUBLICA_CAMINHO))
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        
        KeyFactory keyFact = KeyFactory.getInstance("RSA"); //inicia uma keyfactory que consegue interpretar dados da chaves rsa e converte em objectos java
        
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFact.generatePrivate(
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(conteudoDaKeyPrivada))
        ); //transforma o conteudo de base64 para bytes

        RSAPublicKey publicKey = (RSAPublicKey) keyFact.generatePublic(
                new X509EncodedKeySpec(Base64.getDecoder().decode(conteudoDaKeyPublica))
        );

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build(); //retorna a chave publica e a privada
    }
    
    @Bean
    public JwtEncoder jwtEncoder(RSAKey chaveRsa)
    {
        var jwkSet = new ImmutableJWKSet<SecurityContext>(new JWKSet(chaveRsa));
        
        return new NimbusJwtEncoder(jwkSet);
    } //assinar e emitir a o jwt
    
    @Bean
    public JwtDecoder jwtDecodificador(RSAKey chaveRsa) throws Exception
    {
        return NimbusJwtDecoder.withPublicKey(chaveRsa.toRSAPublicKey()).build();
    }// validar jwt recebido
        
    
}
