/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author eabarca
 */
public class Ficha {

    private final int filas;
    private final int columnas;
    private String color;
    private boolean rotacionIzq90;
    private boolean rotacionDer90;
    private boolean rotacionIzq180;
    private boolean rotacionDer180;
    private Celda[][] matriz;

    public Ficha(int filas, int columnas, String color, boolean[] piezas) {
        this.filas = filas;
        this.columnas = columnas;
        this.color = color;
        this.setMatriz(piezas);
    }

    public Ficha(Celda[][] matriz) {
        this.filas = matriz.length;
        this.columnas = matriz[0].length;
        this.color = matriz[0][0].getColor();
        this.setMatriz(matriz);
    }

    public Ficha(Ficha ficha) {
        this.columnas = ficha.getColumnas();
        this.filas = ficha.getFilas();
        this.color = ficha.getColor();
        this.setMatriz(ficha.getMatriz());
    }

    @Override
    public String toString() {
        String out
                = "\nFilas: " + this.filas
                + "\nColumnas: " + this.columnas
                + "\nRotación -90: " + this.rotacionIzq90
                + "\nRotación +90: " + this.rotacionDer90
                + "\nRotación -180: " + this.rotacionIzq180
                + "\nRotación +180: " + this.rotacionDer180 + "\n";

        for (int fila = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++) {
                out += "\t" + Celda.COLORES_MAP.get(this.getColor()) + (this.matriz[fila][col].isOcupada() ? 1 : 0) + Celda.COLORES_MAP.get("NEGRO");
            }
            out += "\n";
        }

        return out;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isRotacionIzq90() {
        return rotacionIzq90;
    }

    public boolean isRotacionDer90() {
        return rotacionDer90;
    }

    public boolean isRotacionIzq180() {
        return rotacionIzq180;
    }

    public boolean isRotacionDer180() {
        return rotacionDer180;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setMatriz(boolean[] piezas) {
        this.matriz = new Celda[this.filas][this.columnas];

        for (int fila = 0, i = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++, i++) {
                this.matriz[fila][col] = new Celda(piezas[i], this.color);
            }
        }
    }

    public void setMatriz(Celda[][] matriz) {
        this.matriz = new Celda[this.filas][this.columnas];

        for (int fila = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++) {
                this.matriz[fila][col] = new Celda(matriz[fila][col].isOcupada(), this.color);
            }
        }
    }

//    private void replaceMatriz(boolean[][] newMatriz) {
//        this.matriz = newMatriz;
//    }
    public Celda[][] getMatriz() {
        return this.matriz;
    }
    
    public boolean esIgualA(Ficha ficha) {
        boolean resultado = true;
        
        if(this.getColumnas() == ficha.getColumnas() && this.getFilas() == ficha.getFilas()) {
            for (int i=0; i<this.getFilas() && resultado; i++) {
                for (int j=0; j<this.getColumnas() && resultado; j++) {
                    resultado &= this.getMatriz()[i][j].isOcupada() == ficha.getMatriz()[i][j].isOcupada();
                }
            }
        } else {
            resultado = false;
        }
        
        return resultado;
    }
    
    

    public List<Ficha> getRotaciones() {
        List<Ficha> fichas = new ArrayList<Ficha>();
        Ficha fichaRotadaIzq90 = this.rotarIzq90();
        Ficha fichaRotadaDer90 = this.rotarDer90();
        Ficha fichaRotada180 = this.rotar180();

        fichas.add(this);

        if (!this.esIgualA(fichaRotadaIzq90)) {
            this.rotacionIzq90 = true;
            fichas.add(fichaRotadaIzq90);
        }

        if (!fichaRotadaIzq90.esIgualA(fichaRotadaDer90)) {
            this.rotacionDer90 = true;
            fichas.add(fichaRotadaDer90);
        }

        if (!this.esIgualA(fichaRotada180)) {
            this.rotacionIzq180 = true;
            fichas.add(fichaRotada180);
        }

        return fichas;
    }

    private static Celda[][] invertirColumnasMatriz(Celda[][] matriz) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length / 2; col++) {
                Celda aux = new Celda(matriz[fila][col].isOcupada(), matriz[fila][col].getColor());
                matriz[fila][col] = matriz[fila][matriz[fila].length - col - 1];
                matriz[fila][matriz[0].length - col - 1] = aux;
            }
        }

        return matriz;
    }

    private static Celda[][] invertirFilasMatriz(Celda[][] matriz) {
        for (int fila = 0; fila < matriz.length / 2; fila++) {
            Celda[] aux = matriz[fila];
            matriz[fila] = matriz[matriz.length - fila - 1];
            matriz[matriz.length - fila - 1] = aux;
        }

        return matriz;
    }

    private static Celda[][] transponerMatriz(Celda[][] matriz) {
        Celda[][] matrizOut = new Celda[matriz[0].length][matriz.length];

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                matrizOut[col][fila] = new Celda(matriz[fila][col].isOcupada(), matriz[fila][col].getColor());
            }
        }

        return matrizOut;
    }

    public Ficha rotarIzq90() {
        return new Ficha(
                invertirColumnasMatriz(
                        transponerMatriz(this.matriz)));
    }

    public Ficha rotarDer90() {
        return new Ficha(
                invertirFilasMatriz(
                        transponerMatriz(this.matriz)));
    }

    public Ficha rotarDer180() {
        return new Ficha(
                rotarDer90().
                rotarDer90());
    }

    public Ficha rotar180() {
        return new Ficha(
                rotarDer90().
                rotarDer90());
    }

//    public void rotarIzq90() {
//        replaceMatriz(
//                invertirColumnasMatriz(
//                    transponerMatriz(this.matriz)));
//    }
//
//    public void  rotarDer90() {
//        replaceMatriz(
//                invertirFilasMatriz(
//                    transponerMatriz(this.matriz)));
//    }
//
//    public void  rotarDer180() {
//        rotarDer90(); 
//        rotarDer90();
//    }
//
//    public void  rotarIzq180() {
//        rotarIzq90();
//        rotarIzq90();
//    }
}
