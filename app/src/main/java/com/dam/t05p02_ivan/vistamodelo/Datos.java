package com.dam.t05p02_ivan.vistamodelo;

import com.dam.t05p02_ivan.modelo.Alumno;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    private static Datos datos;
    private List<Alumno> mAlumnos;

    private Datos() {
        mAlumnos = new ArrayList<>();
    }

    public static Datos getInstance(){
        if(datos == null){
            datos = new Datos();
        }
        return datos;
    }

    public List<Alumno> getmAlumnos() {
        return mAlumnos;
    }
}
