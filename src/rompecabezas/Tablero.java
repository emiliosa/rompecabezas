/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author eabarca
 */
public class Tablero {

    private int columnas;
    private int filas;
    private String color;
    private Celda[][] matriz;
    private List<Tablero> soluciones;

    public static int cantSoluciones = 0;

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
                out += "\t" + Celda.COLORES_MAP.get(this.matriz[fila][col].getColor()) + (this.matriz[fila][col].isOcupada() ? 1 : 0) + Celda.COLORES_MAP.get(this.color);
            }
            out += "\n";
        }

        out += "\nSoluciones: " + Tablero.cantSoluciones;

        return out;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Arrays.deepHashCode(this.matriz);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tablero other = (Tablero) obj;
        if (!Arrays.deepEquals(this.matriz, other.matriz)) {
            return false;
        }
        return true;
    }

    public Tablero(int filas, int columnas) {
        this.columnas = columnas;
        this.filas = filas;
        this.color = "NEGRO";
        this.matriz = new Celda[filas][columnas];
        this.soluciones = new ArrayList<Tablero>();
        
        for (int fila = 0; fila < this.filas; fila++) {
            for (int col = 0; col < this.columnas; col++) {
                this.matriz[fila][col] = new Celda(false, this.color);
            }
        }
    }

    public boolean isTableroLleno() {
        boolean aux = true;
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                aux &= this.matriz[i][j].isOcupada();
            }
        }
        return aux;
    }

    public boolean casilleroVacio(int fila, int col) {
        return !this.matriz[fila][col].isOcupada();
    }

    public boolean insertarFicha(Ficha ficha, int filaTablero, int colTablero) {
        boolean buscarPosicion = false;
        //recorrer ficha
        for (int filaFicha = 0; filaFicha < ficha.getFilas(); filaFicha++) {
            for (int colFicha = 0; colFicha < ficha.getColumnas(); colFicha++) {
                //si la ficha no se va de rango
                boolean tieneLugar = (filaTablero + filaFicha) < this.filas && (colTablero + colFicha) < this.columnas;
                if (tieneLugar) {
                    boolean choclo
                            = ficha.getMatriz()[filaFicha][colFicha].isOcupada()
                            && this.matriz[filaTablero + filaFicha][colTablero + colFicha].isOcupada();
                    if (choclo) {
                        buscarPosicion = true;
                    }
                }
            }
        }

        if (!buscarPosicion) {
            for (int fila = 0; fila < ficha.getFilas() && (filaTablero + fila) < this.filas; fila++) {
                for (int col = 0; col < ficha.getColumnas() && (colTablero + col) < this.columnas; col++) {
                    boolean ocupada
                            = this.matriz[filaTablero + fila][colTablero + col].isOcupada()
                            || ficha.getMatriz()[fila][col].isOcupada();
                    String colorString
                            = this.matriz[filaTablero + fila][colTablero + col].isOcupada()
                                    ? this.matriz[filaTablero + fila][colTablero + col].getColor()
                                    : ficha.getMatriz()[fila][col].getColor();
                    this.matriz[filaTablero + fila][colTablero + col].setColor(colorString);
                    this.matriz[filaTablero + fila][colTablero + col].setOcupada(ocupada);
                }
            }
        }
        return !buscarPosicion;
    }

    public boolean quitarFicha(Ficha ficha, int filaTablero, int colTablero) {
        for (int fila = 0; fila < ficha.getFilas() && (filaTablero + fila) < this.filas; fila++) {
            for (int col = 0; col < ficha.getColumnas() && (colTablero + col) < this.columnas; col++) {
                if (ficha.getMatriz()[fila][col].isOcupada()) {
                    this.matriz[filaTablero + fila][colTablero + col].setOcupada(false);
                    this.matriz[filaTablero + fila][colTablero + col].setColor(this.color);
                }
            }
        }

        return true;
    }
    
    public boolean solucionExistente() {
        Iterator i = this.soluciones.iterator();
        
        while (i.hasNext()) {
            if (this.equals(i.next())) {
                return true;
            }
        }
        
        return false;
    }

    public void posicionarFichas(Tablero tablero, List<Ficha> fichas) {

        if (tablero.isTableroLleno()) {
            //verificar si la solución ya existe (no funciona bien)
            if (!this.solucionExistente()) {
                Tablero.cantSoluciones++;
                this.soluciones.add(this);
                System.out.println(tablero);
            }
            return;
        }

        //recorrer las piezas
        for (int numFicha = 0; numFicha < fichas.size(); numFicha++) {

            //recorrer tablero
            for (int filaTablero = 0; filaTablero < tablero.getFilas(); filaTablero++) {
                for (int colTablero = 0; colTablero < tablero.getColumnas(); colTablero++) {

                    //todas las rotaciones de cada ficha
                    List<Ficha> fichasRotadas = fichas.get(numFicha).getRotaciones();

                    //recorro cada una de las rotaciones de cada ficha
                    for (int numFichaRotada = 0; numFichaRotada < fichasRotadas.size(); numFichaRotada++) {

                        //si pudo insertar la ficha en el tablero
                        if (tablero.insertarFicha(fichasRotadas.get(numFichaRotada), filaTablero, colTablero)) {
                            
                            //si la solución no existe
                            //if (!this.solucionExistente()) {
                                
                                //quitar la ficha insertada de las fichas disponibles
                                fichas.remove(numFicha);

                                //backtracking
                                posicionarFichas(tablero, fichas);

                                //luego de probar todas las alternativas de esta etapa 
                                //quitar la ficha insertada y continuar con las siguientes
                                tablero.quitarFicha(fichasRotadas.get(numFichaRotada), filaTablero, colTablero);
                                fichas.add(numFicha, fichasRotadas.get(numFichaRotada));
                            //}
                        }
                    }
                }
            }
        }
    }

}
