/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.consis.acsel.repository;

import com.consis.acsel.entity.Movimiento;
import java.util.Date;
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
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
    
    @Query("Select m from Movimiento m where m.fkNumerocuenta=:numeroCuenta and m.fecha between :fechaInicio and :fechaFinal")
    List<Movimiento> buscarMovimientosRangoFecha(@Param("numeroCuenta")Integer numeroCuenta,@Param("fechaInicio")Date fechaInicio,@Param("fechaFinal")Date fechaFinal);
    
    @Query("Select max(m.idmovimiento) from Movimiento m where m.fkNumerocuenta=:numeroCuenta")
    Optional<Integer> buscarMovimientoReciente(@Param("numeroCuenta")Integer numeroCuenta);
    
    @Query("Select m from Movimiento m where m.fecha between :fechaInicio and :fechaFin and m.fkNumerocuenta=:numeroCuenta and m.tipomovimiento='Retiro'")
    List<Movimiento> obtenerListadoMovimientosRetiroDiario(@Param("fechaInicio")Date fechaInicio, @Param("fechaFin")Date fechaFin, @Param("numeroCuenta")Integer numeroCuenta);
}
