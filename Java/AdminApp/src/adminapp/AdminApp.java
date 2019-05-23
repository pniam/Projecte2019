/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author Usuari
 */
public class AdminApp {

    public JFrame f2 = new JFrame("AdminApp");
    JButton jb1 = new JButton();
    JButton jb2 = new JButton();
    JTextField jtfau1;      //JTextField Add empresa Dades Usuari 1 (mail)
    JTextField jtfau2;      //JTextField Add empresa Dades Usuari 2 (Password)
    JTextField jtfan1;      //JTextField Add empresa Dades Negoci 1 (Nif Cif)
    JTextField jtfan2;      //JTextField Add empresa Dades Negoci 2 (Nom Comercial)
    JTextField jtfaa1;      //JTextField Add empresa Dades Adreca 1 (Carrer)
    JTextField jtfaa2;      //JTextField Add empresa Dades Adreca 2 (Numero)
    JTextField jtfaa3;      //JTextField Add empresa Dades Adreca 3 (Localitat)
    JTextField jtfaa4;      //JTextField Add empresa Dades Adreca 4 (CP)
    JTextField jtfaa5;      //JTextField Add empresa Dades Adreca 5 (Provincia)
    JTextField jtfaa6;      //JTextField Add empresa Dades Adreca 6 (Direccio)
    
    JTextField jtfsn1;      //JTextField Search empresa Dades Negoci 1 (id) //readonly
    JTextField jtfsn2;      //JTextField Search empresa Dades Negoci 2 (NifCif)
    JTextField jtfsn3;      //JTextField Search empresa Dades Negoci 3 (nomComercial)
    JTextField jtfsn4;      //JTextField Search empresa Dades Negoci 4 (idAdreca) //readonly
    JCheckBox jcbsn5;       //JCheckBox Search empresa Dades Negoci 5 (baixa)
    DefaultTableModel model;                                        
    JTable taula;                                                   //Taula per mostrar empreses
    Class[] columnClass = new Class[] {Integer.class,String.class}; //Tipus de dades de la taula
    String nomColum[] = {"Id","Empresa"};                           //Nom de capçaleres de la taula
    ArrayList<Empresa> negocis; //L'utilitzem per agafar els negocis a omplir de la taula
    GestionarButtons gb = new GestionarButtons();   //Gestio de botons
    ConnexioMySql con = new ConnexioMySql();        //Connexio a db
    JTextField jtfef1;      //JTextField Empresa Filtre 1 Nom Comercial
    JTextField jtfef2;      //JTextField Empresa Filtre 2 Nif/Cif
    JTextField jtfef3;      //JTextField Empresa Filtre 3 Ciutat
    JTextField jtfef4;      //JTextField Empresa Filtre 4 Provincia
    
    JPanel jpWest= new JPanel(new MigLayout());     //JPanel per la taula principal de Search
    JPanel jpSouth= new JPanel(new MigLayout());     //JPanel per filtres
    AdminApp()
    {
        loadMenuApp();
        f2.setPreferredSize(new Dimension(800,600));
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.pack();
        f2.setLocationRelativeTo(null);
        f2.setVisible(true);
        f2.setResizable(false);    
    }
    void loadMenuApp(){
        JMenuBar mb = new JMenuBar();           
        JMenu m1 = new JMenu("Empreses");        
        m1.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem m11= new JMenuItem("Afegir Empresa");
        m11.setMnemonic(KeyEvent.VK_A);
        m11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        m11.addActionListener(gb);
        
        JMenuItem m12= new JMenuItem("Llistat empreses");
        m12.setMnemonic(KeyEvent.VK_L);
        m12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
        m12.addActionListener(gb);
        
        JMenuItem m16 =new JMenuItem("Surt");
        m16.setMnemonic(KeyEvent.VK_S);
        m12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        m16.addActionListener(gb);
        
        m1.add(m11);
        m1.add(m12);
        m1.addSeparator();
        m1.addSeparator();
        m1.add(m16);
        
        mb.add(m1); // afegir 1 opció de menú (Arxiu) en la Barra de menú
        f2.setJMenuBar(mb); // assignar Barra de menú al Frame
        //loadFormAddEmp();
    }
    
    void loadFormSearchEmp(){
        jpWest.removeAll();
        jpWest.repaint();
        model = new DefaultTableModel();
        taula = new JTable(model){
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return columnClass[column];
            }
        };                      
                                
        taula.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int pos = 0 ; pos<nomColum.length;pos++)
        {                   
            model.addColumn(nomColum[pos]);
        }                   
        jtfef1 = new JTextField();           
        jtfef2 = new JTextField();           
        jtfef3 = new JTextField();           
        jtfef4 = new JTextField();  
        /*Puc cridar a una funcio que examini si x camps globals tenen dades i depen de si en tenen o no executar una cerca o una altre*/
        negocis = omplaNegoci();
        pintaTaula();
                       
        JScrollPane scrollPane = new JScrollPane(taula,
                                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        jpWest.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        jpWest.add(scrollPane);
        /***************/
        /*   Filtres   */
        /***************/
        JLabel jlfet = new JLabel("Filtres: ");        //JLabel Filtres Empresa Titol
        JLabel jlfe1 = new JLabel("Nom Comercial");    
        JLabel jlfe2 = new JLabel("Nif/Cif");
        JLabel jlfe3 = new JLabel("Ciutat");
        JLabel jlfe4 = new JLabel("Provincia");
                 
        JButton filtra = new JButton("Filtra");
        filtra.setPreferredSize(new Dimension(100,20));
        filtra.addActionListener(gb);
        
        jpSouth.add(jlfet,"span");
        jpSouth.add(jlfe1);
        jpSouth.add(jtfef1,"span,gapleft 20, width 150:160:170");
        jpSouth.add(jlfe2);
        jpSouth.add(jtfef2,"span,gapleft 20, width 150:160:170");
        jpSouth.add(jlfe3);
        jpSouth.add(jtfef3,"span,gapleft 20, width 150:160:170");
        jpSouth.add(jlfe4);
        jpSouth.add(jtfef4,"gapleft 20,span, width 150:160:170");
        jpSouth.add(filtra,"span, width 260:270:280");
        jpWest.add(jpSouth, BorderLayout.SOUTH);    //Afegim els filtres a la part south de la taula
        f2.add(jpWest, BorderLayout.WEST);
/************************************************************************************/
        JPanel jpEast = new JPanel(new MigLayout());
        JButton edi = new JButton("Edita");
        JButton eli = new JButton("Elimina");
        JButton modi = new JButton("Modifica");
        edi.setPreferredSize(new Dimension(100,20));
        eli.setPreferredSize(new Dimension(100,20));
        modi.setPreferredSize(new Dimension(100,20));
        edi.addActionListener(gb);
        eli.addActionListener(gb);
        modi.addActionListener(gb);
        JLabel jlau1 = new JLabel("id");
        jtfsn1= new JTextField();        
        jtfsn1.setEditable(false);
        JLabel jlau2 = new JLabel("NifCif");
        jtfsn2= new JTextField();
        JLabel jlau3 = new JLabel("Nom Comercial");
        jtfsn3= new JTextField();
        JLabel jlau4 = new JLabel("adreca");
        jtfsn4 = new JTextField();
        jtfsn4.setEditable(false);
        JLabel jlau5 = new JLabel("Baixa");
        jcbsn5 = new JCheckBox();;
        jpEast.add(edi,"wrap,span");
        jpEast.add(eli,"wrap,span");
        jpEast.add(jlau1);
        jpEast.add(jtfsn1,"gapleft 20, width 30:35:40,wrap");
        jpEast.add(jlau2);
        jpEast.add(jtfsn2, "gapleft 20, width 80:85:90,wrap");
        jpEast.add(jlau3);
        jpEast.add(jtfsn3, "gapleft 20, width 150:155:160,wrap");
        jpEast.add(jlau4);
        jpEast.add(jtfsn4, "gapleft 20, width 30:35:40,wrap");
        jpEast.add(jlau5);
        jpEast.add(jcbsn5, "gapleft 20, width 150:155:160,wrap");
        jpEast.add(modi,"wrap,span");
        
        f2.add(jpEast, BorderLayout.CENTER);        
        f2.pack();          
    }

    void loadFormAddEmp(){
        jpWest.removeAll();
        jpWest.repaint();
         
        JLabel info001 = new JLabel("Dades acces aplicacio:");
        JLabel jlau1 = new JLabel("Mail"); 
        jtfau1  = new JTextField();
        JLabel jlau2 = new JLabel("Password");
        jtfau2  = new JTextField();
        JLabel info002 = new JLabel("Dades personals empresa:");
        JLabel jlan1 = new JLabel("Nif / Cif");
        jtfan1  = new JTextField();
        JLabel jlan2 = new JLabel("Nom Comercial");
        jtfan2  = new JTextField();
        JLabel info003 = new JLabel("Direccio Empresa:");
        JLabel jlaa1 = new JLabel("carrer");
        jtfaa1  = new JTextField();
        JLabel jlaa2 = new JLabel("Numero");
        jtfaa2  = new JTextField();
        JLabel jlaa3 = new JLabel("Localitat");
        jtfaa3  = new JTextField();
        JLabel jlaa4 = new JLabel("Codi postal");
        jtfaa4  = new JTextField();
        JLabel jlaa5 = new JLabel("Provincia");
        jtfaa5  = new JTextField();
        JLabel jlaa6 = new JLabel("Direccio");
        jtfaa6  = new JTextField();  //AQUEST L'OMPLIM AUTOMATICAMENT
        jb1 = new JButton("Afageix");
        jb2 = new JButton("Neteja");
        
        jtfaa1.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+" "+jtfaa3.getText()+" "+jtfaa5.getText()+" "+jtfaa4.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa2.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()<48 || e.getKeyCode()>57){
                    JOptionPane.showMessageDialog(f2, "caracter no admes en el numero de direccio");
                    jtfaa2.setText("");
                }
            }
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+" "+jtfaa3.getText()+" "+jtfaa5.getText()+" "+jtfaa4.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa3.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+" "+jtfaa3.getText()+" "+jtfaa5.getText()+" "+jtfaa4.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa4.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+" "+jtfaa3.getText()+" "+jtfaa5.getText()+" "+jtfaa4.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa5.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+" "+jtfaa3.getText()+" "+jtfaa5.getText()+" "+jtfaa4.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        
        jb1.addActionListener(gb);
        jb2.addActionListener(gb);
        jtfaa6.setEditable(false);
        // GridLayout, 0 quantitat de files -- il·limitat
        // 2 és la quantitat de columnes
        jb1.setVisible(true);
        jpWest.add(info001, "grow, span");
        jpWest.add(jlau1);                             //mail
        jpWest.add(jtfau1, "width 250:300:350");              
        jpWest.add(jlau2);                             //password
        jpWest.add(jtfau2, "wrap, grow, span, width 200:250:300");
        jpWest.add(info002, "grow, span");
        jpWest.add(jlan1);                             //nifcif
        jpWest.add(jtfan1,"width 250:300:350");
        jpWest.add(jlan2);                             //nomComercial
        jpWest.add(jtfan2, "wrap, grow, span, width 200:250:300");
        jpWest.add(info003, "grow, span");
        jpWest.add(jlaa1);                             //carrer
        jpWest.add(jtfaa1, "width 250:300:350");
        jpWest.add(jlaa2);                             //numero
        jpWest.add(jtfaa2, "width 20:35:40");
        jpWest.add(jlaa4);                             //Codi Postal
        jpWest.add(jtfaa4, "wrap, grow, span, width 50:65:70");
        jpWest.add(jlaa3);                             //localitat
        jpWest.add(jtfaa3, "width 250:300:350");
        jpWest.add(jlaa5);                             //Provincia
        jpWest.add(jtfaa5, "wrap, grow, span, width 250:300:350");
        jpWest.add(jlaa6);                             //Direccio
        jpWest.add(jtfaa6, "wrap, grow, span");
        jpWest.add(jb1);
        jpWest.add(jb2);
        
        f2.add(jpWest, BorderLayout.CENTER);
        f2.pack();
    }
    
    public static void main(String[] args) {
        AdminApp a = new AdminApp();
    }
 
    
    class GestionarButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Afegir Empresa")){
                loadFormAddEmp();
            }
            else if(e.getActionCommand().equals("Llistat empreses")){
                loadFormSearchEmp();
            }
            else if(e.getActionCommand().equals("Surt")){
                int opcion = JOptionPane.showConfirmDialog(null, "Segur que vols sortir?", "Sortir", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) { //The ISSUE is here
                   System.exit(0);
                } 
            }
            else if(e.getActionCommand().equals("Filtra")){
                buidarTaula();
                negocis = omplaNegoci();
                pintaTaula();
            }
            else if(e.getActionCommand().equals("Neteja"))
            {
                netejaForm(); 
            }
            else if(e.getActionCommand().equals("Afageix"))
            {
                if(isInteger(jtfaa2.getText())){
                    int numero = Integer.parseInt(jtfaa2.getText());
                    //Adreca               
                    Adreca a = new Adreca(jtfaa1.getText(),numero,jtfaa3.getText(),jtfaa4.getText(),jtfaa5.getText(),jtfaa6.getText()); 
                    //Usuari
                    Usuari u = new Usuari(jtfau1.getText(),jtfau2.getText());
                    int idUsuari = 0;
                    int idAdreca = 0;
                    if(!con.checkAdreca(a) && !con.checkUsuari(u) && jtfan1.getText().length()>0 && jtfan2.getText().length()>0){
                        if(con.afageixUsuari(u)){
                            idUsuari = con.buscaUsuari(u);
                        }
                        if(con.afageixAdreca(a)){
                            idAdreca = con.buscaAdreca(a);
                        }
                        if(idUsuari!=0 && idAdreca!=0){
                            Empresa emp = new Empresa(idUsuari,jtfan1.getText(),jtfan2.getText(),idAdreca);
                            if(con.afageixEmpresa(emp)){   //un cop arribats aqui tenim clar que ha afegit adreca i usuari, si passa qualsevol cosa i no pot afegir negoci procedim a eliminar els registres ja inserits de usuari i adreca
                                JOptionPane.showMessageDialog(f2, "L'usuari ha estat inserti correctament");
                            }
                            if(con.buscaEmpresa(emp)<=0){
                                JOptionPane.showMessageDialog(f2, "L'empresa no s'ha afegit per un error inesperat");
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(f2, "Aquesta adreça i/o usuari ja estan regitrats o has deixat camps buits.");
                        netejaForm();
                    }
                }else{
                    JOptionPane.showMessageDialog(f2, "Introdueix un numero de direccio correcte");
                }
            }
            else if(e.getActionCommand().equals("Modifica")){
                if(jtfsn1.getText().equals(null) || jtfsn1.getText().length()<=0 
                    || jtfsn2.getText().equals(null) || jtfsn2.getText().length()<=0 
                    || jtfsn3.getText().equals(null) || jtfsn3.getText().length()<=0
                    || jtfsn4.getText().equals(null) || jtfsn4.getText().length()<=0){
                    JOptionPane.showMessageDialog(f2, "No pots deixar camps buits.");
                }else{
                    int baixa; 
                    if(jcbsn5.isSelected()){
                        baixa = 1;
                    }else{
                        baixa = 0;
                    }
                    Integer idUsuari = Integer.parseInt(jtfsn1.getText());
                    if(con.modificaEmpresa(idUsuari,jtfsn2.getText(),jtfsn3.getText(),baixa)){
                        JOptionPane.showMessageDialog(f2, "S'ha modificat correctament.");
                        negocis = omplaNegoci();
/************************************************/
                        int index =taula.getSelectedRow();
                        model.removeRow(taula.getSelectedRow());
                        Object []fila = new Object[] {idUsuari, jtfsn3.getText()};
                        model.addRow(fila);
                    }else{
                        JOptionPane.showMessageDialog(f2, "No s'ha pogut modificar l'usuari.");
                    }
                }
            }
            else if(e.getActionCommand().equals("Edita")){
                if(taula.getSelectedRow()!=-1){
                    Empresa emp = con.getEmpresaFromId((int) taula.getModel().getValueAt(taula.getSelectedRow(), 0));
                    Integer id = emp.getIdUsuari();
                    jtfsn1.setText(id.toString());
                    jtfsn2.setText(emp.getNif());
                    jtfsn3.setText(emp.getNomComercial());
                    Integer adreca = emp.getIdAdreca();
                    jtfsn4.setText(adreca.toString());
                    Integer baixa = emp.getBaixa();
                    if(baixa == 1){
                        jcbsn5.setSelected(true);
                    }else{
                        jcbsn5.setSelected(false);
                    }
                }else{
                    JOptionPane.showMessageDialog(f2, "Selecciona una empresa.");
                }
            }
        }
    }
    protected void buidarTaula() {
        ((DefaultTableModel)taula.getModel()).setNumRows(0);
    }
    void pintaTaula(){
        for (int pos=0; pos<negocis.size();pos++)
        {                       
            Object []obj = new Object[] {negocis.get(pos).getIdUsuari(), negocis.get(pos).getNomComercial()};
            model.insertRow(pos,obj);
        }    
    }
    
    void netejaForm()
    {
        jtfau1.setText(""); 
        jtfau2.setText(""); 
        jtfan1.setText(""); 
        jtfan2.setText(""); 
        jtfaa1.setText(""); 
        jtfaa2.setText(""); 
        jtfaa3.setText(""); 
        jtfaa4.setText(""); 
        jtfaa5.setText(""); 
        jtfaa6.setText("");
    }
    ArrayList<Empresa> omplaNegoci()
    {
        if(jtfef1.getText().length()<=0 && jtfef2.getText().length()<=0 && jtfef3.getText().length()<=0 && jtfef4.getText().length()<=0 ){
            negocis = con.getAllNegocis();
        }else{
            negocis = con.getNegocisFiltered(jtfef1.getText(),jtfef2.getText(),jtfef3.getText(),jtfef4.getText());
        }
        return negocis;
    }
    
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
    
}
