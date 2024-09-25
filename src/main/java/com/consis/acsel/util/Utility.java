/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author Orange
 */
public class Utility {
    public static final Double LIMIT_OPETATION=1000D;
    public static final String LIMIT_OPERATION_MESSAGE="Cupo diario excedido";
    public static final String HORA_INICIAL = " 00:00:00";
    public static final String HORA_FINAL = " 23:59:59";
    public static final SimpleDateFormat FECHA_FORMATO = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String TIPO_MOVIMIENTO_DEBITO="Retiro";
    public static final String TIPO_MOVIMIENTO_CREDITO="Deposito";
    public static final String FONDOS_INSUFICIENTES="Saldo no disponible";
    
    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }
    
    public static Double convertToPositive(Double monto){
        if(monto<0)
            monto=monto*(-1);
        return monto;
    }
    
    public static Double convertToNegative(Double monto){
        if(monto>0)
            monto=monto*(-1);
        return monto;
    }
    
    public static Date aplicarFormatoFecha(String fecha) throws ParseException{
        return FECHA_FORMATO.parse(fecha);
    }
}
