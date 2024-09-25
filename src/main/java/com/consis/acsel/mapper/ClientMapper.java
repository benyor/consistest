/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.consis.acsel.mapper;

import com.consis.acsel.dto.ClienteDTO;
import com.consis.acsel.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Orange
 */
@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper( ClientMapper.class );
 
    //@Mapping(source = "nombre", target = "nombre")
    //@Mapping(source = "contrasena", target = "contrasena")
    //@Mapping(source = "estado", target = "estado")
    Cliente clienteDtoToCliente(ClienteDTO client);
    
    /*private String nombre;
    private String direccion;
    private String telefono;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String contrasena;
    private String estado;*/
}
