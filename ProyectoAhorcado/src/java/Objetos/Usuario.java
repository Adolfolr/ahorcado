/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import BaseDatos.BBDD;

/**
 *
 * @author rafael
 */
public class Usuario {
    String nombre;
    int puntuacion;
    String palabra;
    float media;
    BBDD bbdd = new BBDD();
   
    public Usuario(String nombre) {
        this.nombre = nombre;
 }
    
    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        int p = bbdd.puntuacionUsuario(nombre);
        bbdd.destroy();
        return p;
    }

    public void setPuntuacion(int puntuacionGanada) {
        int puntuacionAntigua = getPuntuacion();
        puntuacion = puntuacionAntigua + puntuacionGanada;
        bbdd.actualizarPuntuacion(nombre,puntuacion);
    }

    public String getPalabra() {
        palabra = bbdd.palabra(nombre);
        bbdd.destroy();
        return palabra;
    }

    public void setPalabra(String palabra) {
        bbdd.siguientePalabra(nombre);
    }
    
    public float getMedia() {
        media = bbdd.saberMedia(nombre);
        return media;
    }

    public void setMedia(boolean gana0pierde) {
        bbdd.actualizarMediaParidasGanadas(nombre, gana0pierde);
    }
}
