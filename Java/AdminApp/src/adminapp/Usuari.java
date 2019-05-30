/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class Usuari {
    String mail;
    String password;
    int rol;

    public int getRol() {
        return rol;
    }

    public Usuari(String mail, String password) {
        try {
            setMail(mail);
            Cheats.convertToMD5(password);
            setPassword(Cheats.convertToMD5(password));
            
            this.rol = 2;
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if(mail==null || mail.length()<=0){
            throw new RuntimeException("El mail no pot ser Null ni buit");
        }
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password==null || password.length()<=0){
            throw new RuntimeException("La contrasenya no pot ser Nulla ni buida");
        }
        this.password = password;
    }
}
