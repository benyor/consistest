/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.consis.acsel.repository;

import com.consis.acsel.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Orange
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{
    
    @Query("Select c.idpersona from Cliente c where c.nombre=:nombre and c.estado='True'")
    Integer findPersonaId(@Param("nombre") String nombre);
    
    @Query("Select c.idpersona from Cliente c where c.nombre=:nombre")
    Integer findReportePersonaId(@Param("nombre") String nombre);
    
    @Query("Select c from Cliente c where c.idpersona=:idCliente")
    Optional<Cliente> findClientePersonaId(@Param("idCliente") Integer idCliente);
    
    @Modifying
    @Query("Update Cliente set estado='False' where idpersona=:id")
    void borrarCliente(@Param("id") Integer id);
}
