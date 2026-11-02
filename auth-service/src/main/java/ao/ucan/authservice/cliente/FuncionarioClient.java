/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.cliente;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author pradopc
 */
@Component
public class FuncionarioClient 
{
    private final RestClient clienteRest;
    
    public FuncionarioClient()
    {
        this.clienteRest = RestClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }
    
    public boolean funcionarioExiste(String funcId, String token)
    {
        try
        {
            clienteRest.get()
                    .uri("/api/funcionarios/{id}", funcId)
                    .header(HttpHeaders.AUTHORIZATION, token) //PASSA O TOKEN
                    .retrieve()
                    .toBodilessEntity();
            
            return true;
        }
        catch (HttpClientErrorException.NotFound e){
            return false;
        }
    }
}
