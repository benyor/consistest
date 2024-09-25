/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Orange
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportesResponseDTO {
    private Date fecha;
    private String cliente;
    private Integer numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private String estado;
    private Double movimiento;
    private Double saldoDisponible;
}
