/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.dto;

/**
 *
 * @author pradopc
 */    

public class RegistroUserRequest 
{

    private String username;
    private String password;
    private String role;
    private String funcionarioId;

    public RegistroUserRequest() {
    }

    public RegistroUserRequest(String username, String password,
                                 String role, String funcionarioId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.funcionarioId = funcionarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(String funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
   

