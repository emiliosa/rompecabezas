/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.List;

/**
 *
 * @author eabarca
 */
public class Tablero {

    private int columnas;
    private int filas;
    private boolean[][] matriz;
    private boolean tableroLleno;

    public int getColumnas() {
        return columnas;
    }

    public int getFilas() {
        return filas;
    }

    @Override
    public String toString() {
        String out = "\n";

        for (int fila = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++) {
                out += "\t" + (this.matriz[fila][col] ? 1 : 0);
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
    
    public Tablero(Tablero tablero) {
        this.columnas = tablero.columnas;
        this.filas = tablero.filas;
        //this.matriz = tablero.matriz;
        this.matriz = new boolean[this.filas][this.columnas];
        for (int fila = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++) {
                this.matriz[fila][col] = tablero.matriz[fila][col];
            }
        }
    }

    public boolean isTableroLleno() {
        boolean aux = true;
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                aux &= this.matriz[i][j];
            }
        }
        return aux;
    }

    public boolean casilleroVacio(int fila, int col) {
        return !this.matriz[fila][col];
    }

    public boolean insertarFicha(Ficha ficha, int filaTablero, int colTablero) {
        boolean buscarPosicion = false;
        //recorrer ficha
        for (int filaFicha = 0; filaFicha < ficha.getFilas(); filaFicha++) {
            for (int colFicha = 0; colFicha < ficha.getColumnas(); colFicha++) {
                //si la ficha no se va de rango
                boolean tieneLugar = (filaTablero + filaFicha) < this.filas && (colTablero + colFicha) < this.columnas;
                if (tieneLugar) {
                    boolean choclo = ficha.getMatriz()[filaFicha][colFicha] && this.matriz[filaTablero + filaFicha][colTablero + colFicha];
                    if (choclo){
                        buscarPosicion = true;
                    }
                }
            }
        }
        
        if (!buscarPosicion) {
            for (int fila = 0; fila < ficha.getFilas() && (filaTablero + fila) < this.filas; fila++) {
                for (int col = 0; col < ficha.getColumnas() && (colTablero + col) < this.columnas; col++) {
                    this.matriz[filaTablero + fila][colTablero + col] = this.matriz[filaTablero + fila][colTablero + col] || ficha.getMatriz()[fila][col];
                }
            }
        }
        return !buscarPosicion;
    }
    
    public boolean quitarFicha(Ficha ficha, int filaTablero, int colTablero) {
        for (int fila = 0; fila < ficha.getFilas() && (filaTablero + fila) < this.filas; fila++) {
            for (int col = 0; col < ficha.getColumnas() && (colTablero + col) < this.columnas; col++) {
                if (ficha.getMatriz()[fila][col]) {
                    this.matriz[filaTablero + fila][colTablero + col] = false;
                }
            }
        }
        
        return true;
    }

    public Tablero posicionarFichas(Tablero tablero, List<Ficha> fichas, int numFicha) {
        
        for (int i = 0; i < tablero.getFilas() && !tablero.isTableroLleno() && numFicha < fichas.size(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                

                if (tablero.insertarFicha(fichas.get(numFicha), i, j)) {
                    if (tablero.isTableroLleno()) {
                        System.out.println(tablero);
                    } else {
                        posicionarFichas(tablero, fichas, ++numFicha);
                        tablero.quitarFicha(fichas.get(numFicha), i, j);
                    }
                }

                if (fichas.get(numFicha).isRotacionDer90()) {
                    fichas.get(numFicha).rotarDer90();
                    if (tablero.insertarFicha(fichas.get(numFicha), i, j)) {
                        fichas.get(numFicha).rotarIzq90();
                        if (tablero.isTableroLleno()) {
                            System.out.println(tablero);
                        } else {
                            posicionarFichas(tablero, fichas, ++numFicha);
                            tablero.quitarFicha(fichas.get(numFicha), i, j);
                        }
                    } else {
                        fichas.get(numFicha).rotarIzq90();
                    }
                }

                if (fichas.get(numFicha).isRotacionDer180()) {
                    fichas.get(numFicha).rotarDer180();
                    if (tablero.insertarFicha(fichas.get(numFicha), i, j)) {
                        fichas.get(numFicha).rotarIzq180();
                        if (tablero.isTableroLleno()) {
                            System.out.println(tablero);
                        } else {
                            posicionarFichas(tablero, fichas, ++numFicha);
                            tablero.quitarFicha(fichas.get(numFicha), i, j);
                        }
                    } else {
                        fichas.get(numFicha).rotarIzq180();
                    }
                }
                if (fichas.get(numFicha).isRotacionIzq90()) {
                    fichas.get(numFicha).rotarIzq90();
                    if (tablero.insertarFicha(fichas.get(numFicha), i, j)) {
                        fichas.get(numFicha).rotarDer90();
                        if (tablero.isTableroLleno()) {
                            System.out.println(tablero);
                        } else {
                            posicionarFichas(tablero, fichas, ++numFicha);
                            tablero.quitarFicha(fichas.get(numFicha), i, j);
                        }
                    } else {
                        fichas.get(numFicha).rotarDer90();
                    }
                }
                if (fichas.get(numFicha).isRotacionIzq180()) {
                    fichas.get(numFicha).rotarIzq180();
                    if (tablero.insertarFicha(fichas.get(numFicha), i, j)) {
                        fichas.get(numFicha).rotarDer180();
                        if (tablero.isTableroLleno()) {
                            System.out.println(tablero);
                        } else {
                            posicionarFichas(tablero, fichas, ++numFicha);
                            tablero.quitarFicha(fichas.get(numFicha), i, j);
                        }
                    } else {
                        fichas.get(numFicha).rotarDer180();
                    }
                }
            }
        }
        
        return tablero;
    }

}
