/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.entity;

import jakarta.persistence.Entity;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 *
 * @author Orange
 */
@Entity
@PrimaryKeyJoinColumn(name = "idcliente")
@Table(name = "cliente")
public class Cliente extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*@Id
    @Basic(optional = false)
    @Column(name = "idcliente")
    private String idcliente;*/
    
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "estado")
    private String estado;

    public Cliente() {
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
