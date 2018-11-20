/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author eabarca
 */
public class Rompecabezas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String line = "4,4,6-(2,3-0,1,1,1,1,0) (1,1-1) (3,2-0,1,1,1,0,1) (2,2-1,1,0,1) (1,1-1) (2,2-1,0,1,1)";
        String tableroData[] = line.split(",");
        Tablero tablero = new Tablero(Integer.valueOf(tableroData[0]), Integer.valueOf(tableroData[1]));
        List<Ficha> fichas = new ArrayList<Ficha>();
        
        fichas.add(new Ficha(2, 3, "ROJO", new boolean[]{false,true,true,true,true,false}));
        fichas.add(new Ficha(1, 1, "CYAN", new boolean[]{true}));
        fichas.add(new Ficha(3, 2, "AMARILLO", new boolean[]{false,true,true,true,false,true}));
        fichas.add(new Ficha(2, 2, "VERDE", new boolean[]{true,true,false,true}));
        fichas.add(new Ficha(1, 1, "MAGENTA", new boolean[]{true}));
        fichas.add(new Ficha(2, 2, "AZUL", new boolean[]{true,false,true,true}));
        
//        System.out.println(fichas.get(0).getRotaciones());
//        System.out.println(fichas.get(1).getRotaciones());
//        System.out.println(fichas.get(2).getRotaciones());
//        System.out.println(fichas.get(3).getRotaciones());
//        System.out.println(fichas.get(4).getRotaciones());
//        System.out.println(fichas.get(5).getRotaciones());

        
//        tablero.insertarFicha(fichas.get(0), 0, 0);
//        tablero.insertarFicha(fichas.get(1), 0, 0);
//        tablero.insertarFicha(fichas.get(2), 0, 0);
//        tablero.insertarFicha(fichas.get(3), 0, 0);
//        tablero.insertarFicha(fichas.get(4), 0, 0);
//        tablero.insertarFicha(fichas.get(5), 0, 0);

        tablero.posicionarFichas(tablero, fichas);
        
        System.out.println(tablero);
    }
    
}
