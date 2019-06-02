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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JPasswordField;
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

    public JFrame f2 = new JFrame("AdminApp");      //Aquest frame es el principal de l'aplicacio
    JFrame f3;                                      //Aquest frame l'utilitzem per mostrar les cates
    JButton jb1 = new JButton();                    //Boton per fer submit del formulari afegir Negoci
    JButton jb2 = new JButton();                    //Boton per netejar el formulari afegir Negoci
    JTextField jtfau1;          //JTextField Add empresa Dades Usuari 1 (mail)
    JPasswordField jtfau2;      //JTextField Add empresa Dades Usuari 2 (Password)
    JTextField jtfan1;          //JTextField Add empresa Dades Negoci 1 (Nif Cif)
    JTextField jtfan2;          //JTextField Add empresa Dades Negoci 2 (Nom Comercial)
    JTextField jtfaa1;          //JTextField Add empresa Dades Adreca 1 (Carrer)
    JTextField jtfaa2;          //JTextField Add empresa Dades Adreca 2 (Numero)
    JTextField jtfaa3;          //JTextField Add empresa Dades Adreca 3 (Localitat)
    JTextField jtfaa4;          //JTextField Add empresa Dades Adreca 4 (CP)
    JTextField jtfaa5;          //JTextField Add empresa Dades Adreca 5 (Provincia)
    JTextField jtfaa6;          //JTextField Add empresa Dades Adreca 6 (Direccio)
    JTextField jtfsn1;          //JTextField Search empresa Dades Negoci 1 (id) //readonly
    JTextField jtfsn2;          //JTextField Search empresa Dades Negoci 2 (NifCif)
    JTextField jtfsn3;          //JTextField Search empresa Dades Negoci 3 (nomComercial)
    JTextField jtfsn4;          //JTextField Search empresa Dades Negoci 4 (idAdreca) //readonly
    JCheckBox jcbsn5;           //JCheckBox Search empresa Dades Negoci 5 (baixa)
    DefaultTableModel model;    //Model Taula Negocis                                        
    DefaultTableModel model2;   //Model Taula Cates
    JTable taula;               //Taula per mostrar empreses
    JTable taula2;              //Taula per mostrar Cates
    Class[] columnClass = new Class[] {Integer.class,String.class,String.class};    //Tipus de dades de la taula empresa
    Class[] columnClass2 = new Class[] {String.class,Double.class};                 //Tipus de dades de la taula cates
    String nomColum[] = {"Id","Empresa","Baixa"};                                   //Nom de capçaleres de la taula
    String nomColum2[] = {"Producte","Valoracio"};                                  //Nom de capçaleres de la taula
    ArrayList<Empresa> negocis;                         //L'utilitzem per agafar els negocis a omplir de la taula
    ArrayList<CataFinalitzada> cates;                   //L'utilitzem per agafar les cates a omplir de la taula
    GestionarButtons gb = new GestionarButtons();       //Gestio de botons
    ConnexioMySql con = new ConnexioMySql();            //Connexio a db
    JTextField jtfef1;          //JTextField Empresa Filtre 1 Nom Comercial
    JTextField jtfef2;          //JTextField Empresa Filtre 2 Nif/Cif
    JTextField jtfef3;          //JTextField Empresa Filtre 3 Ciutat
    JTextField jtfef4;          //JTextField Empresa Filtre 4 Provincia
    JPanel jpWest= new JPanel(new MigLayout());     //JPanel per la taula principal de Search
    JPanel jpSouth= new JPanel(new MigLayout());    //JPanel per filtres
    JPanel jpEast= new JPanel(new MigLayout());     //JPanel per Botons i formulari modificacio
    JPanel jpSouthWest= new JPanel(new MigLayout());//JPanel per Botons i formulari modificacio
    
    //Constructor 
    AdminApp(){
        loadMenuApp();  //carreguem el menu
        f2.setPreferredSize(new Dimension(800,600));
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.pack();
        f2.setLocationRelativeTo(null);
        f2.setVisible(true);
        f2.setResizable(false);    
    }
    
    //Funcio cargar menu
    void loadMenuApp(){
        JMenuBar mb = new JMenuBar();       //Crear objecte menu
        JMenu m1 = new JMenu("Empreses");   //Creem element principal menu
        m1.setMnemonic(KeyEvent.VK_E);      //Li assignem tecla d'acces rapid alt+tecla
        
        JMenuItem m11 = new JMenuItem("Afegir Empresa");    //Primer element del submenu ens portara al formuari per afegir una empresa
        m11.setMnemonic(KeyEvent.VK_A);
        m11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));    //li posem access rapid mitjançant control+lletra
        m11.addActionListener(gb);          //Li assignem event en ser premut
        
        JMenuItem m12 = new JMenuItem("Llistat empreses");  //Segon element del submenu ens portara al llistat de formularis
        m12.setMnemonic(KeyEvent.VK_L);
        m12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
        m12.addActionListener(gb);
        
        JMenuItem m16 = new JMenuItem("Surt");          //boto sortir
        m16.setMnemonic(KeyEvent.VK_S);
        m12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        m16.addActionListener(gb);
        
        //Afegim els elements al element Empresa
        m1.add(m11);
        m1.add(m12);
        m1.addSeparator();
        m1.add(m16);
        //Afegim Empresa al menubar
        mb.add(m1); 
        f2.setJMenuBar(mb); // assignar Barra de menú al Frame
    }
    
    /*
        Funcio per carregar el llistat d'empreses
    */
    void loadFormSearchEmp(){
        //Afegir i llistar utilitzen el mateix frame, per tant si l'administrador va a l'altre
        //funcionalitat abans hem de netejar el frame
        clearFrame();
        
        //creem model i taula on es mostrara el llistat d'empreses
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
        taula.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    //permetem que unicament pugui seleccionar un element
        for (int pos = 0 ; pos<nomColum.length;pos++)
        {                   
            model.addColumn(nomColum[pos]);     //Afegim els titols
        }
        negocis = omplaNegoci();    //Omplim negocis amb arraylist de totes les empreses
        pintaTaula();               //Omplim la taula              
        JScrollPane scrollPane = new JScrollPane(taula,
                                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        jpWest.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        jpWest.add(scrollPane);
        
        /***************/
        /*   Filtres   */
        /***************/
        JLabel jlfet = new JLabel("Filtres: ");         //JLabel Filtres Empresa Titol
        JLabel jlfe1 = new JLabel("Nom Comercial");     //JLabel Filtres Empresa Nom Comercial
        JLabel jlfe2 = new JLabel("Nif/Cif");           //JLabel Filtres Empresa NifCif
        JLabel jlfe3 = new JLabel("Ciutat");            //JLabel Filtres Empresa Ciutat
        JLabel jlfe4 = new JLabel("Provincia");         //JLabel Filtres Empresa Provincia
        jtfef1 = new JTextField();           
        jtfef2 = new JTextField();           
        jtfef3 = new JTextField();           
        jtfef4 = new JTextField();
        
        JButton filtra = new JButton("Filtra");             //Boto per filtrar
        filtra.setPreferredSize(new Dimension(100,20));     
        filtra.addActionListener(gb);                       //Assigna event al boto de filtrar
        
        //Afegim elements a panel south
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
        f2.add(jpWest, BorderLayout.WEST);          //Afegim la taula en el canto west del frame
        /*
            Botons modificacio taula
        */
        JButton edi = new JButton("Edita");         //boto editar   
        JButton eli = new JButton("Elimina");       //boto Eliminar / Donar de baixa
        JButton modi = new JButton("Modifica");     //boto Modificar (confirmar canvis)
        JButton cate = new JButton("Llista cates"); //boto Llistar cates
        edi.setPreferredSize(new Dimension(100,20));
        eli.setPreferredSize(new Dimension(100,20));
        modi.setPreferredSize(new Dimension(100,20));
        cate.setPreferredSize(new Dimension(100,20));
        edi.addActionListener(gb);
        eli.addActionListener(gb);
        modi.addActionListener(gb);
        cate.addActionListener(gb);
        /*
            Creem formulari modificacio dades
        */
        JLabel jlau1 = new JLabel("id");
        jtfsn1= new JTextField();        
        jtfsn1.setEditable(false);  //El id no el deixem modificar
        
        JLabel jlau2 = new JLabel("NifCif");
        jtfsn2= new JTextField();
        
        JLabel jlau3 = new JLabel("Nom Comercial");
        jtfsn3= new JTextField();
        
        JLabel jlau4 = new JLabel("adreca");
        jtfsn4 = new JTextField();
        jtfsn4.setEditable(false);  //La Adreca no es pot modificar
        
        JLabel jlau5 = new JLabel("Baixa");
        jcbsn5 = new JCheckBox();
        jcbsn5.setEnabled(false);   //Inicialment el checkbox de baixa esta deshabilitat, l'habilitarem en x casos
        
        /*
            Afegim tot el formulari i botons de iteraccio amb la taula a la zona East
        */
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
        jpEast.add(cate,"wrap,span");
        
        f2.add(jpEast, BorderLayout.CENTER);        
        f2.pack();          
    }
    /*
        Funcio netejar frame
        S'eliminen els panels i es creen de nou
    */
    void clearFrame(){
        f2.remove(jpSouth); 
        f2.remove(jpWest);
        f2.remove(jpEast);
        jpSouth = new JPanel(new MigLayout());
        jpWest = new JPanel(new MigLayout());
        jpEast = new JPanel(new MigLayout());
    }
    /*
        Funcio crear formulari insercio d'empresa
    */
    void loadFormAddEmp(){
        clearFrame();   //Es neteja el frame
        /*
            Es creen tots els camps de formulari juntament amb els seus labels corresponents
        */
        JLabel info001 = new JLabel("Dades acces aplicacio:");
        JLabel jlau1 = new JLabel("Mail"); 
        jtfau1  = new JTextField();
        JLabel jlau2 = new JLabel("Password");
        jtfau2  = new JPasswordField();
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
        jtfaa6  = new JTextField();  
        jtfaa6.setEditable(false);  //AQUEST L'OMPLIM AUTOMATICAMENT
        jb1 = new JButton("Afageix");
        jb2 = new JButton("Neteja");
        
        //utilitzem keyListeners per omplir automaticament el jtfaa6
        jtfaa1.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+", "+jtfaa4.getText()+" "+jtfaa3.getText()+", "+jtfaa5.getText());
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
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+", "+jtfaa4.getText()+" "+jtfaa3.getText()+", "+jtfaa5.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa3.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+", "+jtfaa4.getText()+" "+jtfaa3.getText()+", "+jtfaa5.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa4.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()<48 || e.getKeyCode()>57){
                    JOptionPane.showMessageDialog(f2, "caracter no admes en el Codi Postal");
                    jtfaa4.setText("");
                }
            }
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+", "+jtfaa4.getText()+" "+jtfaa3.getText()+", "+jtfaa5.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        jtfaa5.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { 
                jtfaa6.setText(jtfaa1.getText()+", "+jtfaa2.getText()+", "+jtfaa4.getText()+" "+jtfaa3.getText()+", "+jtfaa5.getText());
            }
            public void keyTyped(KeyEvent e) {}
        });
        
        jb1.addActionListener(gb);
        jb2.addActionListener(gb);
        /*
            Afegim tots els elements al panell
        */
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
        f2.add(jpWest, BorderLayout.CENTER);    //Afegim el panell al frame
        f2.pack();
    }
    
    class GestionarButtons implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Si prem Afegir empresa carguem funcio loadFormAddEmp
            if(e.getActionCommand().equals("Afegir Empresa")){  
                loadFormAddEmp();
            }
            /*
                Si prem Llista cates comprovem que hagi seleccionat una empresa
                sino mostrem missatge d'error
                En cas que estigui seleccionada en recollim l'id de l'empresa seleccionada
                i omplim un arraylist de les seves cates, el pasem com a parametre a llistatCates
            */
            else if(e.getActionCommand().equals("Llista cates")){ 
                if(taula.getSelectedRow()!=-1){                 
                    int idEmpresa = (int) taula.getModel().getValueAt(taula.getSelectedRow(), 0);   
                    ArrayList<CataFinalitzada> cates = con.getCatesTancades(idEmpresa); 
                    llistatCates(cates);        
                }else{          
                    JOptionPane.showMessageDialog(f2, "Has de seleccionar una empresa per poder eliminar-la");
                }
            }
            /*
                En cas que premi eliminar comprovem si hi ha una empresa seleccionada
                Si n'hi ha n'agafem l'id i en el cas que tingui cates preguntem confirmacio
                Si confirma donem de baixa l'empresa
                Si no hi ha empresa seleccionada mostrem avis
                I en cas que no tingui cates directament donem de baixa sense demanar confirmacio
                Un cop finalitzat hem de refrescar la taula, per tant tornem a carregar loadFormSearchEmp
                buidem la taula, reomplim la taula amb els nous valors i la tornem a pintar
            */
            else if(e.getActionCommand().equals("Elimina")){    
                if(taula.getSelectedRow()!=-1){                 
                    int idEmpresa = (int) taula.getModel().getValueAt(taula.getSelectedRow(), 0);
                    if(con.teCates(idEmpresa)){
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog (null, "N'estas segur? Aquest negoci te cates registrades, n'estas segur d'eliminar-lo?","Atenció!!",dialogButton);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            con.baixaEmpresa(idEmpresa);
                        }
                    }else{
                        con.baixaEmpresa(idEmpresa);
                    }
                    loadFormSearchEmp();
                    buidarTaula();
                    negocis = omplaNegoci();
                    pintaTaula();
                }else{
                    JOptionPane.showMessageDialog(f2, "Has de seleccionar una empresa per poder eliminar-la");
                }
            }
            /*
                En cas de premer LListat empreses carreguem funcio loadFOrmSearchEmp
            */
            else if(e.getActionCommand().equals("Llistat empreses")){
                loadFormSearchEmp();
            }
            /*
                Si prem sortir demanem confirmacio 
            */
            else if(e.getActionCommand().equals("Surt")){
                int opcion = JOptionPane.showConfirmDialog(null, "Segur que vols sortir?", "Sortir", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) { //The ISSUE is here
                   System.exit(0);
                } 
            }
            /*
                Si prem Filtra buidem la taula, carguem de nou les dades i repintem
            */
            else if(e.getActionCommand().equals("Filtra")){
                buidarTaula();
                negocis = omplaNegoci();
                pintaTaula();
            }
            /*
                Si prem neteja buidem els camps de formulari
            */
            else if(e.getActionCommand().equals("Neteja"))
            {
                netejaForm(); 
            }
            /*
                Si prem afageix
                Primer comprovo que el camp numero de direccio sigui correcte
                En cas de ser-ho el guardo i creo un element de classe Adreca
                I un element de classe Usuari
                Comprovo que aquest usuari i aquesta adreca no estiguin ja en la db i que els camps propis
                de la empresa no siguin buits
                en el cas que tot sigui correcte fins aqui, ja podem inserir l'adreca i l'usuari i recollir-ne l'id
                per inserir el negoci, mostro missatge informatiu de si ha anat be o no
            */
            else if(e.getActionCommand().equals("Afageix"))
            {
                if(isInteger(jtfaa2.getText()) && isInteger(jtfaa4.getText())){
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
                            if(con.afageixEmpresa(emp)){   
                                JOptionPane.showMessageDialog(f2, "L'usuari ha estat inserti correctament");
                                netejaForm();
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
                    JOptionPane.showMessageDialog(f2, "Introdueix un numero de direccio i cp correctes");
                }
            }
            /*
                En cas de premer Modificar
                comprovo que els camps no estiguin ni buits ni nulls
                comprovo si el checkbox esta seleccionat o no, i el deshabilito (perque no es pugui premer despres)
                recullo l'id de la empresa en questio i la modifico
            */
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
                    jcbsn5.setEnabled(false);
                    Integer idUsuari = Integer.parseInt(jtfsn1.getText());
                    if(con.modificaEmpresa(idUsuari,jtfsn2.getText(),jtfsn3.getText(),baixa)){
                        JOptionPane.showMessageDialog(f2, "S'ha modificat correctament.");
                        buidarTaula();
                        negocis = omplaNegoci();
                        pintaTaula();
                    }else{
                        JOptionPane.showMessageDialog(f2, "No s'ha pogut modificar l'usuari.");
                    }
                }
            }
            /*
                Si prem el boto Edita
                comprovo si hi ha alguna empresa seleccionada
                EN cas afirmatiu creo un objecte Empresa
                i omplo els camps del formulari, en el cas que l'empresa estigui donada de baixa
                habilito el boto baixa per si es vol reafegir
            */
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
                        jcbsn5.setEnabled(true);
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
    /*
        Funcio per buidar Taula
        Assigno la dimensio de la taula a 0
    */
    protected void buidarTaula() {
        ((DefaultTableModel)taula.getModel()).setNumRows(0);
    }
    /*
        Funcio per pintar taula
        Per cada element de l'Arraylist de negocis creo una Row a la taula
    */
    void pintaTaula(){
        for (int pos=0; pos<negocis.size();pos++)
        {                       
            String baixa;
            if(negocis.get(pos).getBaixa()==0){
                baixa = "No";
            }else{
                baixa = "Si";
            }
            Object []obj = new Object[] {negocis.get(pos).getIdUsuari(), negocis.get(pos).getNomComercial(),baixa};
            model.insertRow(pos,obj);
        }    
    }
    /*
        Funcio Neteja Form
        netejo tots els camps del formulari
    */
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
    /*
        Funcio omplaNegoci
        Si no hi ha cap filtre seleccionat crido a funcio que retorna tot els negocis, 
        en cas contrari filtro segons les dades que hi hagi
    */
    ArrayList<Empresa> omplaNegoci()
    {
        if((jtfef1==null && jtfef2==null && jtfef3==null && jtfef4==null) || (jtfef1.getText().length()<=0 && jtfef2.getText().length()<=0 && jtfef3.getText().length()<=0 && jtfef4.getText().length()<=0)){
            negocis = con.getAllNegocis();
        }else{
            String nif;
            String nom;
            String ciutat;
            String provincia;
            if(jtfef1==null){
                nif = "";
            }else{
                nif = jtfef1.getText();
            }
            if(jtfef2==null){
                nom = "";
            }else{
                nom = jtfef2.getText();
            }
            if(jtfef3==null){
                ciutat = "";
            }else{
                ciutat = jtfef3.getText();
            }
            if(jtfef4==null){
                provincia = "";
            }else{
                provincia = jtfef4.getText();
            }
            negocis = con.getNegocisFiltered(nif,nom,ciutat,provincia);
        }
        return negocis;
    }

    /*
        Funcio llistatCates
        Passant-lo un Arraylist de cates per parametre
        en el cas que el frame 3 no sigui null (es a dir, ja s'havia obert anteriorment)
        el netejo, sino el creo i li afageixo una taula amb les cates
    */
    void llistatCates(ArrayList<CataFinalitzada> a){
        if(f3!=null){
            f3.remove(jpSouthWest);
            jpSouthWest= new JPanel(new MigLayout()); 
        }
        f3 = new JFrame("Llistat Cates");
        f3.setPreferredSize(new Dimension(500,350));
        f3.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f3.addWindowListener(new OnTancar());
        f3.pack();
        f3.setLocationRelativeTo(null);
        f3.setVisible(true);
        f3.setResizable(false);    
        model2 = new DefaultTableModel();
        taula2 = new JTable(model2){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            @Override
            public Class<?> getColumnClass(int column) {
                return columnClass2[column];
            }
        };   
        taula2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int pos = 0 ; pos<nomColum2.length;pos++)
        {                   
            model2.addColumn(nomColum2[pos]);
        }       
        for (int pos=0; pos<a.size();pos++)
        {                       
            Object []obj = new Object[] {a.get(pos).getProducte(), a.get(pos).getValoracio()};
            model2.insertRow(pos,obj);
        }   
        JScrollPane scrollPane = new JScrollPane(taula2,
                                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        jpSouthWest.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        jpSouthWest.add(scrollPane);
        f3.add(jpSouthWest,BorderLayout.WEST);
    }
    /*
        Funcio netejaISurt per al sortir de el frame3 de cates netejo la taula i tanco el frame
    */
    void netejaISurt(){
        ((DefaultTableModel)taula2.getModel()).setNumRows(0);
        f3.dispose();
    }
    /*
        Classe OnTancar
        especifica que fer en premer la creu en el frame 3
    */
    class OnTancar extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            netejaISurt();
        }
    }
    /*
        Funcio isInteger
        comprova si una string es possible parsejar a integer
    */
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
