package com.programabit.mediguard;

import com.google.gson.annotations.SerializedName;

public class MisGuardiasDto {
    @SerializedName("centroSalud")
    private String centroSalud;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("turno")
    private String turno;

    public MisGuardiasDto(String centroSalud, String fecha, String turno) {
        this.centroSalud = centroSalud;
        this.fecha = fecha;
        this.turno = turno;
    }

    public MisGuardiasDto() {
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
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
}
