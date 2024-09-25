/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ClienteDTO {
    @NotBlank(message = "Nombre del Cliente no puede ser vacio.")
    private String nombre;
    private String direccion;
    private String telefono;
    private String genero;
    private Integer edad;
    private String identificacion;
    @NotNull(message = "La contrasena no puede ser nula.")
    private String contrasena;
    @NotEmpty(message = "Por favor ingrese un estado True o False.")
    private String estado;
}
