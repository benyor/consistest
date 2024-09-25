/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.controller;

import com.consis.acsel.dto.ClienteDTO;
import com.consis.acsel.dto.CuentaUpdateDTO;
import com.consis.acsel.dto.ListadoCuentasDTO;
import com.consis.acsel.dto.ListadoMovimientoDTO;
import com.consis.acsel.dto.ResponseDTO;
import com.consis.acsel.exception.ExceptionHandlerAcsel;
import com.consis.acsel.service.APIService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Orange
 */
@RestController
public class APIController {
    
    @Autowired
    APIService apiService;
    
    @PostMapping("/cuentas")
    public ResponseEntity<?> crearCuentas(@RequestBody ListadoCuentasDTO model){
        ResponseDTO resp = new ResponseDTO();
        try{
            if(model.getCuentas().size()==0)
                throw new ExceptionHandlerAcsel("Listado de Cuentas no puede ser vacio.");
        }catch(ExceptionHandlerAcsel e){
            resp.setCode(400);
            resp.setMessage("Listado de Cuentas no puede ser vacio.");
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
        resp = apiService.crearCuentas(model);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else if(resp.getCode()==404)
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @PutMapping("/cuentas/{id}")
    public ResponseEntity<?> actualizarCuentas(@RequestBody CuentaUpdateDTO model,@RequestParam("id") Integer id){
        ResponseDTO resp = apiService.actualizarCuenta(model,id);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<?> borrarCuenta(@PathVariable Integer id){
        ResponseDTO resp = apiService.borrarCuenta(id);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @PostMapping("/clientes")
    public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO model){
        ResponseDTO resp = apiService.crearCliente(model);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else if(resp.getCode()==400)
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> actualizarClientes(@RequestBody ClienteDTO model,@RequestParam("idCliente") Integer idCliente){
        ResponseDTO resp = apiService.actualizarCliente(model,idCliente);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> borrarCliente(@PathVariable Integer id){
        ResponseDTO resp = apiService.borrarCliente(id);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @PostMapping("/movimientos")
    public ResponseEntity<?> crearMovimientos(@RequestBody ListadoMovimientoDTO model){
        ResponseDTO resp = new ResponseDTO();
        try{
            if(model.getMovimientos().size()==0)
                throw new ExceptionHandlerAcsel("Listado de Movimientos no puede ser vacio.");
        }catch(ExceptionHandlerAcsel e){
            resp.setCode(400);
            resp.setMessage("Listado de Movimientos no puede ser vacio.");
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
        resp = apiService.crearMovimientos(model);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else if(resp.getCode()==400)
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        else if(resp.getCode()==401)
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(resp);
        else if(resp.getCode()==404)
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<?> borrarMovimiento(@PathVariable Integer id){
        ResponseDTO resp = apiService.borrarMovimiento(id);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
    
    @GetMapping("/reportes")
    public ResponseEntity<?> consultarReportes(@RequestParam("fecha") String fecha, @RequestParam("cliente") String cliente){
        ResponseDTO resp = apiService.consultarReportes(fecha,cliente);
        if(resp.getCode()==200)
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(resp);
        if(resp.getCode()==404)
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(resp);
        else
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resp);
    }
}
