package com.example.aplicatieecomercejava;

import java.io.Serializable;

public class Produs implements Serializable {
    private String nume;
    private String descriere;
    private float pret;
    private boolean esteDisponibil;
    String idFirebase;

    public Produs() {
    }

    public Produs(String nume, String descriere, float pret, boolean esteDisponibil) {
        this.nume = nume;
        this.descriere = descriere;
        this.pret = pret;
        this.esteDisponibil = esteDisponibil;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public boolean isEsteDisponibil() {
        return esteDisponibil;
    }

    public void setEsteDisponibil(boolean esteDisponibil) {
        this.esteDisponibil = esteDisponibil;
    }

    public Produs(String nume, String descriere, float pret, boolean esteDisponibil, String idFirebase) {
        this.nume = nume;
        this.descriere = descriere;
        this.pret = pret;
        this.esteDisponibil = esteDisponibil;
        this.idFirebase = idFirebase;
    }
}
