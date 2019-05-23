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
public class Adreca {
    String carrer;
    int numero;
    String localitat;
    String codiPostal;
    String provincia;
    String direccio;
    
    Adreca(String carrer, int numero, String localitat, String codiPostal, String provincia, String direccio){
        setCarrer(carrer);
        setNumero(numero);
        setLocalitat(localitat);
        setCodiPostal(codiPostal);
        setProvincia(provincia);
        setDireccio(direccio);
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        if(carrer==null || carrer.length()<=0){
            throw new RuntimeException("El carrer no pot ser Null ni buit");
        }
        this.carrer = carrer;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        if(localitat==null || localitat.length()<=0){
            throw new RuntimeException("La localitat no pot ser Null ni buida");
        }
        this.localitat = localitat;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        if(codiPostal==null || codiPostal.length()<=0){
            throw new RuntimeException("El codi postal no pot ser null ni buit");
        }
        this.codiPostal = codiPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        if(provincia==null || provincia.length()<=0){
            throw new RuntimeException("La provincia no pot ser Nulla ni buida");
        }
        this.provincia = provincia;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        if(direccio==null || direccio.length()<=0){
            throw new RuntimeException("La direccio no pot ser Nulla ni buida");
        }
        this.direccio = direccio;
    }
            
}
