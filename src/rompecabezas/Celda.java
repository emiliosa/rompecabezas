/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eabarca
 */
public class Celda {
    private boolean ocupada;
    private String color;
    public static final Map<String, String> COLORES_MAP;

    static {
        COLORES_MAP = new HashMap<>();
        COLORES_MAP.put("NEGRO", "\033[40m");
        COLORES_MAP.put("ROJO", "\033[41m");
        COLORES_MAP.put("VERDE", "\033[42m");
        COLORES_MAP.put("AMARILLO", "\033[43m");
        COLORES_MAP.put("AZUL", "\033[44m");
        COLORES_MAP.put("MAGENTA", "\033[45m");
        COLORES_MAP.put("CYAN", "\033[46m");
    }
    
    public Celda(boolean ocupada, String color) {
        this.ocupada = ocupada;
        this.color = color;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
