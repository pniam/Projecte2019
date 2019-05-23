/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class ConnexioMySql {
    Connection con = null;

    public ConnexioMySql() {
        con = obraConnexio();
    }
    
    public Connection obraConnexio(){
        Connection aux=null;
        try {
            aux = null;
            String url = "jdbc:mysql://10.133.0.156:3306/projectedb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            aux = DriverManager.getConnection(url, "userProjecte", "userProjecte");
            return aux;
        }catch(Exception ex){
            if (aux != null) {
                try {
                    aux.close();
                } catch (SQLException ex1) {
                    System.out.println(ex1.getMessage());
                }
                aux = null;
            }
            try {
                throw new SQLException("ERROR: "+ex.getClass()+" - "+ ex.getMessage());
            } catch (SQLException ex1) {
                System.out.println(ex1.getMessage());
            }
        }
        return null;
    }
    public void tancaConnexio(){
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex1) {
                System.out.println(ex1.getMessage());
            }
            con = null;
        }
    }
    
    public Boolean checkadmin(String user,String pass){
        con=obraConnexio();
        Statement st;
        Boolean trobat = false;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from usuari where idRol=1 and mail = '"+user+"' and password = '"+pass+"'");
            if (rs.next()) {
                trobat = true;
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return trobat;
    }   
    
    public Boolean checkAdreca(Adreca a){
        con=obraConnexio();
        Statement st;
        Boolean trobat = false;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from adreca where direccio = '"+a.getDireccio()+"'");
            if (rs.next()) {
                trobat = true;
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return trobat;
    }   
    
    public Boolean checkUsuari(Usuari u){
        con=obraConnexio();
        Statement st;
        Boolean trobat = false;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from usuari where mail = '"+u.getMail()+"'");
            if (rs.next()) {
                trobat = true;
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return trobat;
    }
    public void delAdreca(int id){
        con=obraConnexio();
        String query = "delete from adreca where id = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void delUsuari(int id){
        con=obraConnexio();
        String query = "delete from usuari where id = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // jtfef1; Nom Comercial
    // jtfef2; Nif/Cif
    // jtfef3; Ciutat
    // jtfef4; Provincia
    public ArrayList<Empresa> getNegocisFiltered(String nomComercial,String nif,String ciutat,String provincia){
        con=obraConnexio();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<Empresa> list = null;
        try{
            pst = con.prepareStatement("SELECT * FROM negoci n join adreca a on (n.idAdreca = a.id) where n.NifCif like '%"+nif+"%' and n.nomComercial like '%"+nomComercial+"%' and a.localitat like '%"+ciutat+"%' and a.provincia like '%"+provincia+"%'");
            rs = pst.executeQuery();
            list= new ArrayList<Empresa>();
            Empresa e;
            while (rs.next()) {
                e = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                list.add(e);            
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (pst != null){
                    pst.close();
                }            
                if (con != null){
                    con.close();
                }            
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
    public ArrayList<Empresa> getAllNegocis(){
        con = obraConnexio();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<Empresa> list = null;
        try{
            pst = con.prepareStatement("SELECT * FROM negoci;");
            rs = pst.executeQuery();
            list= new ArrayList<Empresa>();
            Empresa e;
            while (rs.next()) {
                e = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                list.add(e);            
            }   
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (pst != null){
                    pst.close();
                }            
                if (con != null){
                    con.close();
                }            
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
    
    public int buscaEmpresa(Empresa e){
        con = obraConnexio();
        Statement st;
        int id = 0;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT idUsuari from negoci where NifCif= '"+e.getNif()+"'");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public Empresa getEmpresaFromId(int id){
        con = obraConnexio();
        Statement st;
        Empresa aux=null;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from negoci where idUsuari = "+id);
            if (rs.next()) {
                aux = new Empresa(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5));
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return aux;
    }
    
    public int buscaAdreca(Adreca a){
        con = obraConnexio();
        Statement st;
        int id = 0;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id from adreca where direccio = '"+a.getDireccio()+"'");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public int buscaUsuari(Usuari u){
        con = obraConnexio();
        Statement st;
        int id = 0;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id from usuari where mail = '"+u.getMail()+"'");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    
    public Boolean modificaEmpresa(int id, String nif, String nom, int baixa){
        con = obraConnexio();
        Boolean modificat = false;
        try {
            con.setAutoCommit(false);
            String s2 = "update negoci set NifCif = ?, nomComercial = ?, baixa = ?"
                    + " where idUsuari = ?";
            PreparedStatement st = con.prepareStatement(s2);
            st = con.prepareStatement(s2);
            st.setString(1,nif);
            st.setString(2,nom);
            st.setInt(3,baixa);
            st.setInt(4,id);
            st.executeUpdate();
            System.out.println("S'ha modificat les dades del negoci");
            con.commit();
            modificat = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return modificat;
    }
    public Boolean afageixUsuari(Usuari u){
        con = obraConnexio();
        Boolean inserit = false;
        try {
            con.setAutoCommit(false);
            String s2 = "INSERT INTO usuari (mail,password,idRol) "
                    + " VALUES (?,?,?) ";
            PreparedStatement st = con.prepareStatement(s2);
            st = con.prepareStatement(s2);
            st.setString(1,u.getMail());
            st.setString(2,u.getPassword());
            st.setInt(3,u.getRol());
            st.executeUpdate();
            System.out.println("S'ha inserit l'usuari");
            con.commit();
            inserit = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return inserit;
    }
    
    public Boolean afageixEmpresa(Empresa e){
        con = obraConnexio();
        Boolean inserit = false;
        try {
            con.setAutoCommit(false);
            String s2 = "INSERT INTO negoci (idUsuari,NifCif,nomComercial,idAdreca,baixa) "
                    + " VALUES (?,?,?,?,?) ";
            PreparedStatement st = con.prepareStatement(s2);
            st = con.prepareStatement(s2);
            st.setInt(1,e.getIdUsuari());
            st.setString(2,e.getNif());
            st.setString(3,e.getNomComercial());
            st.setInt(4,e.getIdAdreca());
            st.setInt(5,e.getBaixa());
            st.executeUpdate();
            System.out.println("S'ha inserit el negoci");
            con.commit();
            inserit = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return inserit;
    }
    
    public Boolean afageixAdreca(Adreca a){
        con = obraConnexio();
        Boolean inserit = false;
        try {
            con.setAutoCommit(false);
            String s2 = "INSERT INTO adreca (carrer, num, localitat, cp, provincia, direccio) "
                    + " VALUES (?,?,?,?,?,?) ";
            PreparedStatement st = con.prepareStatement(s2);
            st = con.prepareStatement(s2);
            st.setString(1,a.getCarrer());
            st.setInt(2,a.getNumero());
            st.setString(3,a.getLocalitat());
            st.setString(4,a.getCodiPostal());
            st.setString(5,a.getProvincia());
            st.setString(6,a.getDireccio());
            st.executeUpdate();
            System.out.println("S'ha inserit la adreca");
            con.commit();
            inserit = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return inserit;
    }
}
