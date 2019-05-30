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
            String url = "jdbc:mysql://127.0.0.1:3306/projectedb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            aux = DriverManager.getConnection(url, "perProjecte", "perProjecte");
            return aux;
        }catch(SQLException ex){
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
    
    public Boolean teCates(int idEmp){
        con=obraConnexio();
        ResultSet rs = null;
        PreparedStatement pst = null;
        Boolean ente = false;
        try{
            pst = con.prepareStatement("select * from cates where idNegoci = ?;");
            pst.setInt(1, idEmp);
            rs = pst.executeQuery();
            if(rs.next()){
                ente = true;
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
        return ente;
    }
    public String getPassword(String user){
        con=obraConnexio();
        Statement st;
        String trobat = null;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT password from usuari where mail = '"+user+"'");
            if (rs.next()) {
                trobat = rs.getString(1);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return trobat;
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
    public void eliminaEmpresa(int idEmpresa){
        con=obraConnexio();
        String query = "delete from usuari where id = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, idEmpresa);
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
    public ArrayList<CataFinalitzada> getCatesTancades(int id){
        con=obraConnexio();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<CataFinalitzada> list = null;
        try{
            pst = con.prepareStatement("select p.nom, avg(pa.valoracio) as valoracio from participacio pa right outer join cates c on (c.id = pa.idCata) join producte p on (p.id = c.idProducte) where c.dataEvent < now() and c.idNegoci = ? group by pa.idCata;");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            list= new ArrayList<CataFinalitzada>();
            CataFinalitzada e;
            while (rs.next()) {
                Double valo = rs.getDouble(2);
                e = new CataFinalitzada(rs.getString(1), valo);
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
                e = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
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
                e = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
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
    
    public Boolean baixaEmpresa(int idEmpresa){
        con = obraConnexio();
        Boolean modificat = false;
        try {
            con.setAutoCommit(false);
            String s2 = "update negoci set baixa = 1"
                    + " where idUsuari = ?";
            PreparedStatement st = con.prepareStatement(s2);
            st = con.prepareStatement(s2);
            st.setInt(1,idEmpresa);
            st.executeUpdate();
            con.commit();
            modificat = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return modificat;
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
            con.commit();
            inserit = true;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return inserit;
    }
}
