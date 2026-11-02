package ao.ucan.authservice.dto;

public class UserResposta {

    private String id;
    private String username;
    private String role;
    private String funcionarioId;
    private boolean ativo;
    private String estadoRegisto;

    public UserResposta() {
    }

    public UserResposta(String id, String username, String role,
                           String funcionarioId, boolean ativo, String estadoRegisto) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.funcionarioId = funcionarioId;
        this.ativo = ativo;
        this.estadoRegisto = estadoRegisto;
    }
    
    

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getFuncionarioId() {
        return funcionarioId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @return the estadoRegisto
     */
    public String getEstadoRegisto() {
        return estadoRegisto;
    }

    /**
     * @param estadoRegisto the estadoRegisto to set
     */
    public void setEstadoRegisto(String estadoRegisto) {
        this.estadoRegisto = estadoRegisto;
    }
}