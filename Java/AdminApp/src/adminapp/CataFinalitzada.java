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
public class CataFinalitzada {
    String producte;
    Double valoracio;
    
    CataFinalitzada(String prod, Double valo){
        setProducte(prod);
        setValoracio(valo);
    }

    public String getProducte() {
        return producte;
    }

    public void setProducte(String producte) {
        if(producte==null || producte.length()<=0){
            throw new RuntimeException("El producte no pot ser null ni buit");
        }
        this.producte = producte;
    }

    public Double getValoracio() {
        return valoracio;
    }

    public void setValoracio(Double valoracio) {
        if(valoracio==null){
            valoracio = 0.0;
        }
        this.valoracio = valoracio;
    }
    
}
