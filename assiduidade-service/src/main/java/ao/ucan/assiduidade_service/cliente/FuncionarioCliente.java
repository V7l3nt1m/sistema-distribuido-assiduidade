package ao.ucan.assiduidade_service.cliente;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class FuncionarioCliente {

    private final RestClient clienteRest;

    public FuncionarioCliente(@Value("${servicos.funcionario.url}") String baseUrl) {
        this.clienteRest = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Boolean funcionarioExiste(String funcionarioId, String token) {
        try {
            clienteRest.get()
                    .uri("/api/funcionarios/{id}", funcionarioId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        } catch (ResourceAccessException ex) {
            throw new IllegalStateException(
                    "O serviço de funcionários está indisponível.",
                    ex
            );
        }
    }
}