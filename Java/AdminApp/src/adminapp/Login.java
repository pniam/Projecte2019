/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author PNIAM
 */
public class Login {

    JFrame f = new JFrame("Login"); //Frame utilitzat en el login
    JTextField jtf1;                //Text field per posar login acces
    JPasswordField jtf2;            //Password field per posar password acces
    JButton jb1;                    //boto entrar
    JButton jb2;                    //boto sortir
    GestionarButtons gb = new GestionarButtons();       //Classe gestio botons
    ConnexioMySql con = new ConnexioMySql();            //Classe gestio DB
        
    Login(){                                            //Constructor login
        f.setPreferredSize(new Dimension(350,125));     
        doLogin();                                      
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setResizable(false);    
    }
    public static void main(String[] args) {
        Login l = new Login();
    }
    /*
    Funcio Do Login ompla el frame amb tot el contingut del formulari login
    */
    void doLogin()
    {
        JLabel jl1 = new JLabel("Username");    //creem els labels necessaris
        JLabel jl2 = new JLabel("Password");
        jtf1  = new JTextField();               
        jtf2 = new JPasswordField();
        jb1 = new JButton("Entra");         
        jb2 = new JButton("Surt");
        jb1.addActionListener(gb);              //assignem escoltador als botons
        jb2.addActionListener(gb);
        JPanel jp1 = new JPanel(new GridLayout(0,2));   //utilitzem un panel e gridLayout per colocar els elements
        /*
            Afegim els elements en el panell
        */
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp1.add(jb1);
        jp1.add(jb2);
        f.add(jp1, BorderLayout.CENTER); //afegim el panel en el frame
    }
    
    /*
        Classe per gestionar botons
    */    
    class GestionarButtons implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Entra")){           //Si prem boto Entra
                try {
                    String user = jtf1.getText();               //Recull el camp de username
                    String pass = Cheats.convertToMD5(jtf2.getText());  //Recull el camp password convertit a md5
                    if(con.checkadmin(user,pass)){          //comprova si les dades estan en la db i si l'usuari es administrador
                        f.dispose();                //en cas afirmatiu ja podem tancar login i entrar en la aplicacio
                        AdminApp a = new AdminApp();    
                    }else{      //sino, vol dir que el login ha estat incorrecte, mostrem avis
                        JOptionPane.showMessageDialog(f, "Aquest usuari no consta o no es administrador.");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            else if(e.getActionCommand().equals("Surt"))    //en el cas de premer sortir preguntem si n'esta segur 
            {
                int opcion = JOptionPane.showConfirmDialog(null, "Segur que vols sortir?", "Sortir", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) { //The ISSUE is here
                   System.exit(0);
                } 
            }
        }
    }
}
