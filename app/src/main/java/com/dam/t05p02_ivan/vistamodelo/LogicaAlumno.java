package com.dam.t05p02_ivan.vistamodelo;

import com.dam.t05p02_ivan.modelo.Alumno;

public class LogicaAlumno {

    public static Alumno existeAlumno(Alumno alumno){
        for (Alumno alu : Datos.getInstance().getmAlumnos()) {
            if(alu.getDni().equalsIgnoreCase(alumno.getDni())){
                return alu;
            }
        }
        return null;
    }

    public static boolean altaAlumno(Alumno alumno){
        if(existeAlumno(alumno) != null){
            return false;
        }

        Datos.getInstance().getmAlumnos().add(alumno);
        return true;
    }

    public static boolean bajaAlumno(Alumno alumno){
        Alumno alumnoABorrar = null;
        if((alumnoABorrar = existeAlumno(alumno)) == null){
            return false;
        }

        Datos.getInstance().getmAlumnos().remove(alumnoABorrar);
        System.out.println(Datos.getInstance().getmAlumnos().size());
        return true;
    }

    public static boolean editarAlumno(Alumno alumno, int posicionAlumno) {
        if(existeAlumno(alumno) == null){
            return false;
        }

        Datos.getInstance().getmAlumnos().set(posicionAlumno,alumno);
        return true;
    }
}
