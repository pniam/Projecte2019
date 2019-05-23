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
public class Usuari {
    String mail;
    String password;
    int rol;

    public int getRol() {
        return rol;
    }

    public Usuari(String mail, String password) {
        setMail(mail);
        setPassword(password);
        this.rol = 2;
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
