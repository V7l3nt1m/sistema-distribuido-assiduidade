/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.funcionarioservico.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 *
 * @author pradopc
 */

@Document(collection = "funcionarios")
public class Funcionario 
{
    @Id
    private String id;
    
    private String nome;
    private String email;
    private String cargo;
    private BigDecimal salarioBase;
    
    public Funcionario()
    {
        
    }
    
    public Funcionario(String nome, String email, String cargo, BigDecimal salarioBase)
    {
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the salarioBase
     */
    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    /**
     * @param salarioBase the salarioBase to set
     */
    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }
    
    
}
