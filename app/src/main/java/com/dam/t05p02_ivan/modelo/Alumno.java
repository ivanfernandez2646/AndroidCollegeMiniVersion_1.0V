package com.dam.t05p02_ivan.modelo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Alumno implements Parcelable {

    private String dni;
    private String nombre;
    private String fechaNac;
    private int ciclo;
    private Bitmap foto;

    public Alumno() {
    }

    protected Alumno(Parcel in) {
        dni = in.readString();
        nombre = in.readString();
        fechaNac = in.readString();
        ciclo = in.readInt();
        foto = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dni);
        dest.writeString(nombre);
        dest.writeString(fechaNac);
        dest.writeInt(ciclo);
        dest.writeParcelable(foto, flags);
    }
}
