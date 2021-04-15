package com.programabit.mediguard.domain;

import com.google.gson.annotations.SerializedName;

public class GuardDto {

    @SerializedName("id")
    private int id;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("turno")
    private String turno;
    @SerializedName("centroSalud")
    private String centroSalud;
    @SerializedName("medico")
    private int medico;
    @SerializedName("disponible")
    private boolean disponible;
    @SerializedName("departamento")
    private String departamento;
    @SerializedName("min_ranking")
    private int min_ranking;

    public GuardDto(int id, String fecha, String turno, String centroSalud,
                    int medico, boolean disponible, String departamento, int min_ranking) {
        this.id = id;
        this.fecha = fecha;
        this.turno = turno;
        this.centroSalud = centroSalud;
        this.medico = medico;
        this.disponible = disponible;
        this.departamento = departamento;
        this.min_ranking = min_ranking;
    }

    public GuardDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public int getMedico() {
        return medico;
    }

    public void setMedico(int medico) {
        this.medico = medico;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getMin_ranking() {
        return min_ranking;
    }

    public void setMin_ranking(int min_ranking) {
        this.min_ranking = min_ranking;
    }
}
