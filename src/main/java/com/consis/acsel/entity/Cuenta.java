/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 *
 * @author Orange
 */
@Entity
@Table(name = "cuenta")
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "numerocuenta")
    private Integer numerocuenta;
    @Column(name = "tipocuenta")
    private String tipocuenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldoinicial")
    private Double saldoinicial;
    @Column(name = "estado")
    private String estado;
    @Column(name = "fk_idcliente")
    private Integer fkIdcliente;

    public Cuenta() {
    }

    public Cuenta(Integer numerocuenta, String tipocuenta, Double saldoinicial, String estado, Integer fkIdcliente) {
        this.numerocuenta = numerocuenta;
        this.tipocuenta = tipocuenta;
        this.saldoinicial = saldoinicial;
        this.estado = estado;
        this.fkIdcliente = fkIdcliente;
    }
    
    

    public Cuenta(Integer numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public Integer getNumerocuenta() {
        return numerocuenta;
    }

    public void setNumerocuenta(Integer numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Double getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(Double saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFkIdcliente() {
        return fkIdcliente;
    }

    public void setFkIdcliente(Integer fkIdcliente) {
        this.fkIdcliente = fkIdcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerocuenta != null ? numerocuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.numerocuenta == null && other.numerocuenta != null) || (this.numerocuenta != null && !this.numerocuenta.equals(other.numerocuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.consis.acsel.entity.Cuenta[ numerocuenta=" + numerocuenta + " ]";
    }
    
}
