/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.Arrays;

/**
 *
 * @author eabarca
 */
public class Ficha {

    private final int filas;
    private final int columnas;
    private boolean rotacionIzq90;
    private boolean rotacionDer90;

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
    private boolean rotacionIzq180;
    private boolean rotacionDer180;
    private boolean[][] matriz;

    public Ficha(int filas, int columnas, boolean[] piezas) {
        this.filas = filas;
        this.columnas = columnas;
        this.setMatriz(piezas);
        this.calcularRotaciones();
    }
    
    @Override
    public String toString() {
        String out = 
                "\nFilas: " + this.filas +
                "\nColumnas: " + this.columnas +
                "\nRotación -90: " + this.rotacionIzq90 +
                "\nRotación +90: " + this.rotacionDer90 +
                "\nRotación -180: " + this.rotacionIzq180 +
                "\nRotación +180: " + this.rotacionDer180 + "\n";
        
        for(int fila = 0; fila < this.filas; fila++) {
            for(int col = 0; col < this.columnas; col++) {
                out += "\t" + (this.matriz[fila][col] ? 1:0);
            }
            out += "\n";
        }
        
        return out;
    }
    
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    private void setMatriz(boolean[] piezas) {
        this.matriz = new boolean[this.filas][this.columnas];
        for (int fila = 0, i = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++, i++) {
                this.matriz[fila][col] = piezas[i];
            }
        }
    }
    
    private void replaceMatriz(boolean[][] newMatriz) {
//        this.matriz = new boolean[newMatriz.length][newMatriz[0].length];
//        for (int fila = 0; fila < this.filas; fila++) {
//            for (int col = 0; col < this.columnas; col++) {
//                this.matriz[fila][col] = newMatriz[fila][col];
//            }
//        }
        this.matriz = newMatriz;
    }
    
    public boolean[][] getMatriz() {
        return this.matriz;
    }

    private void calcularRotaciones() {
        boolean[][] oldMatriz = new boolean[this.matriz.length][this.matriz[0].length];
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[i].length; j++) {
                oldMatriz[i][j] = this.matriz[i][j];
            }
        }
        
        rotarIzq90();
        this.rotacionIzq90 = !Arrays.deepEquals(this.matriz, oldMatriz);
        rotarDer180();
        this.rotacionDer90 = !Arrays.deepEquals(this.matriz, oldMatriz);
        rotarDer90();
        this.rotacionDer180 = !Arrays.deepEquals(this.matriz, oldMatriz);
        rotarDer180();
        //rotar +180 es igual a rotar -180
        this.rotacionIzq180 = this.rotacionDer180;
    }

    private static boolean[][] invertirColumnasMatriz(boolean[][] matriz) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length / 2; col++) {
                boolean aux = matriz[fila][col];
                matriz[fila][col] = matriz[fila][matriz[fila].length - col - 1];
                matriz[fila][matriz[0].length - col - 1] = aux;
            }
        }

        return matriz;
    }

    private static boolean[][] invertirFilasMatriz(boolean[][] matriz) {
        for (int fila = 0; fila < matriz.length / 2; fila++) {
            boolean[] aux = matriz[fila];
            matriz[fila] = matriz[matriz.length - fila - 1];
            matriz[matriz.length - fila - 1] = aux;
        }

        return matriz;
    }

    private static boolean[][] transponerMatriz(boolean[][] matriz) {
        boolean[][] matrizOut = new boolean[matriz[0].length][matriz.length];
        
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                matrizOut[col][fila] = matriz[fila][col];
            }
        }

        return matrizOut;
    }

//    private static boolean[][] rotarIzq90(boolean[][] matriz) {
//        return invertirColumnasMatriz(
//                transponerMatriz(matriz));
//    }
//
//    private static boolean[][] rotarDer90(boolean[][] matriz) {
//        return invertirFilasMatriz(
//                transponerMatriz(matriz));
//    }
//
//    private static boolean[][] rotarDer180(boolean[][] matriz) {
//        return rotarDer90(
//                rotarDer90(matriz));
//    }
//
//    private static boolean[][] rotarIzq180(boolean[][] matriz) {
//        return rotarIzq90(
//                rotarIzq90(matriz));
//    }
    
    public void rotarIzq90() {
        replaceMatriz(
                invertirColumnasMatriz(
                    transponerMatriz(this.matriz)));
    }

    public void  rotarDer90() {
        replaceMatriz(
                invertirFilasMatriz(
                    transponerMatriz(this.matriz)));
    }

    public void  rotarDer180() {
        rotarDer90(); 
        rotarDer90();
    }

    public void  rotarIzq180() {
        rotarIzq90();
        rotarIzq90();
    }
    
    

}
