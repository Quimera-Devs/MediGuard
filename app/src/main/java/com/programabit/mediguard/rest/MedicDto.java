package com.programabit.mediguard.rest;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class MedicDto {

    @SerializedName("Nombre_Apellido")
    private String nombre_apellido;
    @SerializedName("ci")
    private int ci;
    @SerializedName("ranking")
    private int ranking;
    @SerializedName("usuario")
    private int usuario;
    @SerializedName("nroCaja")
    private int nroCaja;
    @SerializedName("telefono")
    private int telefono;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("departamento")
    private String departamento;

    public MedicDto(int ci, String nombre_apellido, int ranking, int nroCaja,
                    int telefono, String departamento, String direccion, int usuario) {
        this.nombre_apellido = nombre_apellido;
        this.ci = ci;
        this.ranking = ranking;
        this.usuario = usuario;
        this.nroCaja = nroCaja;
        this.telefono = telefono;
        this.direccion = direccion;
        this.departamento = departamento;
    }

    public MedicDto() {
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(int nroCaja) {
        this.nroCaja = nroCaja;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

}
