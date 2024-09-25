/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.consis.acsel.repository;

import com.consis.acsel.entity.Cuenta;
import java.util.List;
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
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
    
    @Query("Select c from Cuenta c where c.estado='True' and c.numerocuenta=:numeroCuenta")
    Optional<Cuenta> buscarCuentaActiva(@Param("numeroCuenta") Integer numeroCuenta);
    
    @Query("Select c from Cuenta c where c.fkIdcliente=:idCliente")
    List<Cuenta> buscarCuentasActivas(@Param("idCliente") Integer idCliente);
    
    @Modifying
    @Query("Update Cuenta set estado='False' where numerocuenta=:id")
    void borrarCuenta(@Param("id") Integer id);
}
