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
    
    @Override
    public String toString() {
        String out = "\n";
        
        for(int fila = 0; fila < this.filas; fila++) {
            for(int col = 0; col < this.columnas; col++) {
                out += "\t" + (this.matriz[fila][col] ? 1:0);
            }
            out += "\n";
        }
        
        return out;
    }
    
    public Tablero(int filas, int columnas) {
        this.columnas = columnas;
        this.filas = filas;
        this.matriz = new boolean[filas][columnas];
    }
    
    public boolean casilleroVacio(int fila, int col) {
        return !this.matriz[fila][col];
    }
    
    public void insertarFicha(Ficha ficha) {
        boolean fichaInsertada = false;
        //recorerr tablero
        for (int filaTablero = 0; filaTablero < this.filas && !fichaInsertada; filaTablero++) {
            for (int colTablero = 0; colTablero < this.columnas && !fichaInsertada; colTablero++) {
                //si la ficha entra en alguna posición del tablero
                if (ficha.getFilas() <= (this.filas - filaTablero) && ficha.getColumnas() <= (this.columnas - colTablero)) {    
                    boolean posicionEncontrada = false;
                    //recorrer ficha
                    for (int filaFicha = 0; filaFicha < ficha.getFilas() && !posicionEncontrada; filaFicha++) {
                        for (int colFicha = 0; colFicha < ficha.getColumnas() && !posicionEncontrada; colFicha++) {
                            //si la ficha no se va de rango
                            boolean tieneLugar = 
                                    (filaTablero + filaFicha) < this.filas && 
                                    (colTablero + colFicha) < this.columnas;
                            if (tieneLugar) {
                                posicionEncontrada = posicionEncontrada || (ficha.getMatriz()[filaFicha][colFicha] && this.matriz[filaTablero + filaFicha][colTablero + colFicha]);
                            }
                        }
                    }
                    if (!posicionEncontrada) {
                        fichaInsertada = true;
                        for (int fila = 0; fila < ficha.getFilas() && (filaTablero + fila) < this.filas; fila++) {
                            for (int col = 0; col < ficha.getColumnas() && (colTablero + col) < this.columnas; col++) {
                                this.matriz[filaTablero + fila][colTablero + col] = this.matriz[filaTablero + fila][colTablero + col] || ficha.getMatriz()[fila][col];
                            }
                        }
                    }
                }
            }
        }
    }
    
}
