/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.assiduidade_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;



/**
 *
 * @author pradopc
 */
@Configuration //marca que é um arquivo de configuração
public class JwtConfig 
{    
    private static final String CHAVE_PUBLICA_CAMINHO = "src/main/resources/keys/public.pem";
    
    @Bean
    public JwtDecoder jwtDecodificador() throws Exception
    {
        String conteudoDaChavePublica = Files.readString(Path.of(CHAVE_PUBLICA_CAMINHO))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", "");
        
        byte[] bytesDaChave = Base64.getDecoder().decode(conteudoDaChavePublica); //transforma o conteudo da chave em bytes
        
        KeyFactory keyFact = KeyFactory.getInstance("RSA");
        
        RSAPublicKey chavePublica = (RSAPublicKey) keyFact.generatePublic(new X509EncodedKeySpec(bytesDaChave));
        
        return NimbusJwtDecoder.withPublicKey(chavePublica).build();
        
        
    }
   
   
        
    
}
