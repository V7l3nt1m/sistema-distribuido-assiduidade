/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ao.ucan.authservice.repository;

import ao.ucan.authservice.model.Usuario;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author pradopc
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String> //herdandno funconalidade do mongo
{ //ele vai buscar queris já feitas para não precisar criar coisas como findAll, findbyID,etc
    Optional<Usuario> findByUsername(String username);
    
    boolean existsByUsername(String username); //impedir contas duplicadas
    
}
