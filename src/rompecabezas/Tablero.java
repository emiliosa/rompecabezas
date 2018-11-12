/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

/**
 *
 * @author eabarca
 */
public class Tablero {
    
    private int columnas;
    private int filas;
    private boolean[][] matriz;
    
    public Tablero(int filas, int columnas) {
        this.columnas = columnas;
        this.filas = filas;
        this.matriz = new boolean[filas][columnas];
    }
    
}
