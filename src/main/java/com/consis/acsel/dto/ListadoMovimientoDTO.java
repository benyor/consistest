/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.dto;

import java.util.List;
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
public class ListadoMovimientoDTO {
    private List<MovimientoDTO> movimientos;
}
