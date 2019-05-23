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
import javax.swing.JTextField;

/**
 *
 * @author Usuari
 */
public class Login {

    JFrame f = new JFrame("Login");
    JTextField jtf1;
    JTextField jtf2;
    JButton jb1;    //boto entrar
    JButton jb2;    //boto sortir
    GestionarButtons gb = new GestionarButtons();
    ConnexioMySql con = new ConnexioMySql();
    Login(){
        f.setPreferredSize(new Dimension(350,150));
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
    
    void doLogin()
    {
        JLabel jl1 = new JLabel("Username");
        jtf1  = new JTextField();
        JLabel jl2 = new JLabel("Password");
        jtf2 = new JTextField();
        jb1 = new JButton("Entra");
        jb2 = new JButton("Surt");
        jb1.addActionListener(gb);
        jb2.addActionListener(gb);
        JPanel jp1 = new JPanel(new GridLayout(0,2));
        // GridLayout, 0 quantitat de files -- il·limitat
        // 2 és la quantitat de columnes
        jb1.setVisible(true);
        jtf1.setEditable(true);
        jtf2.setEditable(true);
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp1.add(jb1);
        jp1.add(jb2);
        f.add(jp1, BorderLayout.SOUTH);
        
    }
    
    
    
    class GestionarButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Entra")){
                String user = jtf1.getText();
                String pass = jtf2.getText();
                if(con.checkadmin(user,pass)){
                    f.dispose();
                    AdminApp a = new AdminApp();
                }else{
                    JOptionPane.showMessageDialog(f, "Aquest usuari no consta o no es administrador.");
                }
            }
            else if(e.getActionCommand().equals("Surt"))
            {
                int opcion = JOptionPane.showConfirmDialog(null, "Segur que vols sortir?", "Sortir", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) { //The ISSUE is here
                   System.exit(0);
                } 
            }
        }
    }
}
