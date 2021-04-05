package com.programabit.mediguard;


public class MisGuardiasModel {
    private int id;
    private String centroSalud;
    private String fecha;
    private String turno;

    public MisGuardiasModel(String centroSalud, String fecha, String turno) {
        this.centroSalud = centroSalud;
        this.fecha = fecha;
        this.turno = turno;
    }

    public  String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public  String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public  String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getId() {
        return id;
    }
}
