/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.service;

import com.consis.acsel.dto.ClienteDTO;
import com.consis.acsel.dto.CuentaUpdateDTO;
import com.consis.acsel.dto.ListadoCuentasDTO;
import com.consis.acsel.dto.ListadoMovimientoDTO;
import com.consis.acsel.dto.ReportesResponseDTO;
import com.consis.acsel.dto.ResponseDTO;
import com.consis.acsel.entity.Cliente;
import com.consis.acsel.entity.Cuenta;
import com.consis.acsel.entity.Movimiento;
import com.consis.acsel.exception.ExceptionHandlerAcsel;
import com.consis.acsel.mapper.ClientMapper;
import com.consis.acsel.repository.ClienteRepository;
import com.consis.acsel.repository.CuentaRepository;
import com.consis.acsel.repository.MovimientoRepository;
import com.consis.acsel.util.Utility;
import com.consis.acsel.validator.ObjectsValidator;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Orange
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class APIService {
    
    @Autowired
    ClienteRepository clienteRepository;
    
    @Autowired
    CuentaRepository cuentaRepository;
    
    @Autowired
    MovimientoRepository movimientoRepository;
    
    private final ObjectsValidator<ClienteDTO> postValidator;
    
    @Transactional
    public ResponseDTO crearCliente(ClienteDTO model){
        
        ResponseDTO resp = new ResponseDTO();
        
        var violations = postValidator.validate(model);

        if (!violations.isEmpty()) {
            resp.setCode(400);
            resp.setMessage(String.join("\n", violations));
            return resp;
        }
        
        try{
            Cliente entity = ClientMapper.INSTANCE.clienteDtoToCliente(model);
            clienteRepository.saveAndFlush(entity);
            resp.setCode(200);
            resp.setMessage("Cliente creado.");
            log.info("Cliente creado: ", entity.getIdpersona());
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO actualizarCliente(ClienteDTO model, Integer idCliente){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            Optional<Cliente> entity = clienteRepository.findClientePersonaId(idCliente);
            if(entity.isPresent()){
                entity.get().setContrasena(model.getContrasena());
                entity.get().setDireccion(model.getDireccion());
                entity.get().setEdad(model.getEdad());
                entity.get().setEstado(model.getEstado());
                entity.get().setGenero(model.getGenero());
                entity.get().setIdentificacion(model.getIdentificacion());
                entity.get().setNombre(model.getNombre());
                entity.get().setTelefono(model.getTelefono());
                clienteRepository.saveAndFlush(entity.get());
            }
            resp.setCode(200);
            resp.setMessage("Cliente actualizado.");
            log.info("Cliente actualizado: ", entity.get().getIdpersona());
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO actualizarCuenta(CuentaUpdateDTO model, Integer idCuenta){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            Optional<Cuenta> entity = cuentaRepository.findById(idCuenta);
            if(entity.isPresent()){
                entity.get().setEstado(model.getEstado());
                entity.get().setFkIdcliente(model.getIdCliente());
                entity.get().setSaldoinicial(model.getSaldoInicial());
                entity.get().setTipocuenta(model.getTipoCuenta());
                cuentaRepository.saveAndFlush(entity.get());
            }
            resp.setCode(200);
            resp.setMessage("Cuenta actualizada.");
            log.info("Cuenta actualizada: ", entity.get().getNumerocuenta());
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO crearCuentas(ListadoCuentasDTO model){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            List<Integer> listadoIdClientes= new ArrayList<>();
            model.getCuentas().stream().forEach(t -> {
                try {
                    Integer idCliente=clienteRepository.findPersonaId(t.getCliente());
                    if(idCliente==null)
                        throw new ExceptionHandlerAcsel("El cliente ".concat(t.getCliente()).concat(" no existe"));
                    else
                        listadoIdClientes.add(idCliente);
                } catch (ExceptionHandlerAcsel ex) {
                    log.error("Uno de los clientes suministrados no existe");
                    resp.setCode(404);
                    resp.setMessage("Uno de los clientes suministrados no existe");
                }
            });
            
            if(resp.getCode()!=null && resp.getCode()==404)
                return resp;

            model.getCuentas().forEach(Utility.withCounter((i,x) -> {
                try{
                    Cuenta c = new Cuenta(x.getNumeroCuenta(), x.getTipoCuenta(), x.getSaldoInicial(), x.getEstado(), listadoIdClientes.get(i));
                    cuentaRepository.saveAndFlush(c);
                }catch(Exception ex){
                    log.error("Ocurrio una excepcion: ",ex);
                    resp.setCode(500);
                    resp.setMessage("Ocurrio una excepcion: ".concat(ex.toString()));
                }
            }));
            
            if(resp.getCode()!=null && resp.getCode()==500)
                return resp;
            
            resp.setCode(200);
            resp.setMessage("Cuenta(s) creada(s).");
            log.info("Cuenta(s) creada(s): ");
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO crearMovimientos(ListadoMovimientoDTO model){
        
        ResponseDTO resp = new ResponseDTO();
        LocalDate fechaActual = LocalDate.now();
        String formatLocalDateInicio = String.valueOf(fechaActual.getDayOfMonth())
                .concat("/")
                .concat(String.valueOf(fechaActual.getMonthValue()))
                .concat("/")
                .concat(String.valueOf(fechaActual.getYear()))
                .concat(Utility.HORA_INICIAL);
        
        String formatLocalDateFinal = String.valueOf(fechaActual.getDayOfMonth())
                .concat("/")
                .concat(String.valueOf(fechaActual.getMonthValue()))
                .concat("/")
                .concat(String.valueOf(fechaActual.getYear()))
                .concat(Utility.HORA_FINAL);
        
        try{
            List<Cuenta> listadoCuentas= new ArrayList<>();
            model.getMovimientos().stream().forEach(t -> {
                try {
                    Optional<Cuenta> numeroCuenta=cuentaRepository.buscarCuentaActiva(t.getNumeroCuenta());
                    if(!numeroCuenta.isPresent())
                        throw new ExceptionHandlerAcsel("La cuenta ".concat(t.getNumeroCuenta().toString()).concat(" no existe"));
                    else
                        listadoCuentas.add(numeroCuenta.get());
                } catch (ExceptionHandlerAcsel ex) {
                    log.error("Una de las cuentas suministradas no existe");
                    resp.setCode(404);
                    resp.setMessage("Una de las cuentas suministradas no existe");
                }
            });
            
            if(resp.getCode()!=null && resp.getCode()==404)
                return resp;

            model.getMovimientos().forEach(Utility.withCounter((i,x) -> {
                try{
                    Optional<Integer> idMovimientoMax = movimientoRepository.buscarMovimientoReciente(listadoCuentas.get(i).getNumerocuenta());
                    List<Movimiento> historicoMovimiento = movimientoRepository.obtenerListadoMovimientosRetiroDiario(Utility.aplicarFormatoFecha(formatLocalDateInicio), Utility.aplicarFormatoFecha(formatLocalDateFinal),listadoCuentas.get(i).getNumerocuenta());
                    if(historicoMovimiento!=null && historicoMovimiento.size()>0){
                        if(historicoMovimiento.stream().mapToDouble(z->z.getValor()<0?z.getValor()*-1:z.getValor()).sum()>=Utility.LIMIT_OPETATION && x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)){
                            try{
                                throw new ExceptionHandlerAcsel(Utility.LIMIT_OPERATION_MESSAGE);
                            }catch(ExceptionHandlerAcsel ex){
                                resp.setCode(401);
                                resp.setMessage(Utility.LIMIT_OPERATION_MESSAGE);
                            }
                        }else{
                            Movimiento movReciente = movimientoRepository.findById(idMovimientoMax.get()).get();
                            if(movReciente.getSaldo()<x.getMonto() && x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)){
                                try{
                                    throw new ExceptionHandlerAcsel(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                                }catch(ExceptionHandlerAcsel ex){
                                    resp.setCode(400);
                                    resp.setMessage(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                                }
                            }else{
                                //Movimiento movReciente = movimientoRepository.findById(idMovimientoMax.get()).get();
                                Movimiento m = new Movimiento(new Date(), x.getTipoMovimiento(), x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)?Utility.convertToNegative(x.getMonto()):Utility.convertToPositive(x.getMonto()), movReciente.getSaldo()+x.getMonto(), listadoCuentas.get(i).getNumerocuenta());
                                if(resp.getCode()==null)
                                    movimientoRepository.saveAndFlush(m);
                            }
                        }
                    }else if(!idMovimientoMax.isPresent()){
                        if(listadoCuentas.get(i).getSaldoinicial()==0 && x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)){
                            try{
                                throw new ExceptionHandlerAcsel(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                            }catch(ExceptionHandlerAcsel ex){
                                resp.setCode(400);
                                resp.setMessage(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                            }
                        }else{
                            if(listadoCuentas.get(i).getSaldoinicial()<x.getMonto() && x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)){
                                try{
                                    throw new ExceptionHandlerAcsel(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                                }catch(ExceptionHandlerAcsel ex){
                                    resp.setCode(400);
                                    resp.setMessage(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                                }
                            }else{
                                Movimiento m = new Movimiento(new Date(), x.getTipoMovimiento(), x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)?Utility.convertToNegative(x.getMonto()):Utility.convertToPositive(x.getMonto()), listadoCuentas.get(i).getSaldoinicial()+(x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)?Utility.convertToNegative(x.getMonto()):Utility.convertToPositive(x.getMonto())), listadoCuentas.get(i).getNumerocuenta());
                                if(resp.getCode()==null)
                                    movimientoRepository.saveAndFlush(m);
                            }
                        }
                    }else{
                        Movimiento movReciente = movimientoRepository.findById(idMovimientoMax.get()).get();
                        if(movReciente.getSaldo()<x.getMonto() && x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)){
                            try{
                                throw new ExceptionHandlerAcsel(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                            }catch(ExceptionHandlerAcsel ex){
                                resp.setCode(400);
                                resp.setMessage(Utility.FONDOS_INSUFICIENTES.concat(" para la cuenta: ").concat(listadoCuentas.get(i).getNumerocuenta().toString()));
                            }
                        }else{
                            Movimiento m = new Movimiento(new Date(), x.getTipoMovimiento(), x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)?Utility.convertToNegative(x.getMonto()):Utility.convertToPositive(x.getMonto()), movReciente.getSaldo()+(x.getTipoMovimiento().equals(Utility.TIPO_MOVIMIENTO_DEBITO)?Utility.convertToNegative(x.getMonto()):Utility.convertToPositive(x.getMonto())), listadoCuentas.get(i).getNumerocuenta());
                            if(resp.getCode()==null)
                                movimientoRepository.saveAndFlush(m);
                        }
                    }
                }catch(Exception ex){
                    log.error("Ocurrio una excepcion: ",ex);
                    resp.setCode(500);
                    resp.setMessage("Ocurrio una excepcion: ".concat(ex.toString()));
                }
            }));  
            
            if(resp.getCode()!=null && (resp.getCode()==500 || resp.getCode()==400 || resp.getCode()==401)){
                throw new ExceptionHandlerAcsel(resp.getMessage().concat("|").concat(resp.getCode().toString()));
            }
            resp.setCode(200);
            resp.setMessage("Movimiento(s) creado(s).");
            log.info("Movimiento(s) creado(s): ");
        }catch(ExceptionHandlerAcsel ex){
            resp.setCode(Integer.valueOf(ex.getMessage().substring(ex.getMessage().indexOf("|", 0)+1, ex.getMessage().length())));
            resp.setMessage(ex.getMessage().substring(0, ex.getMessage().indexOf("|", 0)));
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO borrarCliente(Integer id){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            clienteRepository.borrarCliente(id);
            resp.setCode(200);
            resp.setMessage("Cliente borrado.");
            log.info("Cliente borrado: ", id);
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO borrarCuenta(Integer id){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            cuentaRepository.borrarCuenta(id);
            resp.setCode(200);
            resp.setMessage("Cuenta borrada.");
            log.info("Cuenta borrada: ", id);
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO borrarMovimiento(Integer id){
        
        ResponseDTO resp = new ResponseDTO();
        try{
            movimientoRepository.deleteById(id);
            resp.setCode(200);
            resp.setMessage("Movimiento borrado.");
            log.info("Movimiento borrado: ", id);
        }catch(Exception e){
            log.error("Ocurrio una excepcion: ", e);
            resp.setCode(500);
            resp.setMessage("Ha ocurrido un error: ".concat(e.toString()));
        }
        return resp;
    }
    
    @Transactional
    public ResponseDTO consultarReportes(String fechas, String nombreCliente){
        
        ResponseDTO resp = new ResponseDTO();
        
        String fechaTextInicio = fechas.split("-")[0].concat(Utility.HORA_INICIAL);
        String fechaTextFinal = fechas.split("-")[1].concat(Utility.HORA_FINAL);
        Integer idCliente = clienteRepository.findReportePersonaId(nombreCliente);
        if(idCliente==null){
            resp.setCode(404);
            resp.setMessage("Cliente no existe");
            return resp;
        }
        Cliente clienteInfo = clienteRepository.findClientePersonaId(idCliente).get();
            
        List<Cuenta> listadoCuentas = cuentaRepository.buscarCuentasActivas(idCliente);
        if(listadoCuentas.size()==0){
            resp.setCode(404);
            resp.setMessage("Cliente no posee cuentas");
            return resp;
        }
        
        List<ReportesResponseDTO> output = new ArrayList<>(); 
        
        listadoCuentas.forEach(t -> {
            try {
                List<Movimiento> listaMov=movimientoRepository.buscarMovimientosRangoFecha(t.getNumerocuenta(), Utility.aplicarFormatoFecha(fechaTextInicio), Utility.aplicarFormatoFecha(fechaTextFinal));
                if(listaMov.size()>0){
                    ReportesResponseDTO report = new ReportesResponseDTO();
                    report.setCliente(clienteInfo.getNombre());
                    report.setEstado(t.getEstado());
                    report.setFecha(listaMov.get(listaMov.size()-1).getFecha());
                    report.setMovimiento(listaMov.get(listaMov.size()-1).getValor());
                    report.setNumeroCuenta(t.getNumerocuenta());
                    report.setSaldoDisponible(listaMov.get(listaMov.size()-1).getSaldo());
                    report.setSaldoInicial(t.getSaldoinicial());
                    report.setTipoCuenta(t.getTipocuenta());
                    output.add(report);
                }
                
            } catch (ParseException ex) {
                log.error("Formato de fecha incorrecto: ", ex);
                resp.setCode(500);
                resp.setMessage("Formato de fecha incorrecto: ".concat(ex.toString()));
            }
        });
        
        if(resp.getCode()!=null && resp.getCode()==500)
                return resp;
        
        resp.setCode(200);
        resp.setData(output);
        return resp;
    }
}
