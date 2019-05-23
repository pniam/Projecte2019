/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

/**
 *
 * @author Usuari
 */
public class Empresa {
    int idUsuari;
    String nif;
    String nomComercial;
    int idAdreca;
    int baixa;  

    public Empresa(int idUsuari, String nif, String nomComercial, int idAdreca) {
        this.idUsuari = idUsuari;
        setNif(nif);
        setNomComercial(nomComercial);
        this.idAdreca = idAdreca;
        this.baixa = 0;
    }
    public Empresa(int idUsuari, String nif, String nomComercial, int idAdreca, int baixa) {
        this.idUsuari = idUsuari;
        setNif(nif);
        setNomComercial(nomComercial);
        this.idAdreca = idAdreca;
        setBaixa(baixa);
    }

    public void setBaixa(int baixa) {
        this.baixa = baixa;
    }
    
    public int getBaixa() {
        return baixa;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public int getIdAdreca() {
        return idAdreca;
    }

    public void setIdAdreca(int idAdreca) {
        this.idAdreca = idAdreca;
    }
    
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        if(nif==null || nif.length()<=0){
            throw new RuntimeException("El Nif/Cif no pot ser null ni buit");
        }
        this.nif = nif;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        if(nomComercial==null || nomComercial.length()<=0){
            throw new RuntimeException("El nom comercial no pot ser null ni buit");
        }
        this.nomComercial = nomComercial;
    }
    
    
} 
