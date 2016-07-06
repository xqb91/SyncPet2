/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.paciente;

import cl.starlabs.controladores.CaracterJpaController;
import cl.starlabs.controladores.EspecieJpaController;
import cl.starlabs.controladores.HabitadJpaController;
import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.controladores.RazaJpaController;
import cl.starlabs.controladores.UsuariosJpaController;
import cl.starlabs.modelo.Caracter;
import cl.starlabs.modelo.Especie;
import cl.starlabs.modelo.Habitad;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Raza;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import cl.starlabs.herramientas.*;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.vista.agenda.AgendaAtencion;
/**
 *
 * @author Victor Manuel Araya
 */
public class RegistroPaciente extends javax.swing.JFrame {

    //ventanas que usan la aplicacion
    DetallePaciente buscar = null;
    //variables normales
    Mascota tp = null;
    Propietario tq = null;
    EntityManagerFactory emf = null;
    MascotaJpaController jpa = null;
    PropietarioJpaController jpb = null;
    //definicion de herramientas
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasCorreo hc = new HerramientasCorreo();
    HerramientasRut hrut = new HerramientasRut();
    HerramientasTelefono ht = new HerramientasTelefono();
    
    AgendaAtencion aat = null;
    
    public RegistroPaciente() {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarEspecie();
        rellenarCaracteres();
        rellenarSexo();
        rellenarHabitad();
        rellenarGrupoSangre();
        habilitarCampos();
        jpa = new MascotaJpaController(emf);
        jpb = new PropietarioJpaController(emf);
    }
    
    public RegistroPaciente(AgendaAtencion aat) {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarEspecie();
        rellenarCaracteres();
        rellenarSexo();
        rellenarHabitad();
        rellenarGrupoSangre();
        habilitarCampos();
        jpa = new MascotaJpaController(emf);
        jpb = new PropietarioJpaController(emf);
        this.aat = aat;
    }
    
    public RegistroPaciente(Usuarios u) {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarEspecie();
        rellenarCaracteres();
        rellenarSexo();
        rellenarHabitad();
        rellenarGrupoSangre();
        habilitarCampos();
        jpa = new MascotaJpaController(emf);
        jpb = new PropietarioJpaController(emf);
    }
    
    public RegistroPaciente(Usuarios u, Mascota ma, Propietario pro) {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarEspecie();
        rellenarCaracteres();
        rellenarSexo();
        rellenarHabitad();
        rellenarGrupoSangre();
        habilitarCampos();
        jpa = new MascotaJpaController(emf);
        jpb = new PropietarioJpaController(emf);
        this.tp = ma;
        this.tq = pro;
        String sexo;
        if(tp.getSexo() == 'M') {
            sexo = "Macho";
        }else if(tp.getSexo() == 'H') {
            sexo = "Hembra";
        }else{
            sexo = "Hermafrodita";
        }
        rellenarCampos(tp.getNombre(), tp.getFechaNacimiento(), tp.getRaza().getEspecie().getIdEspecie()+": "+tp.getRaza().getEspecie().getNombre(), tp.getRaza().getIdRaza()+": "+tp.getRaza().getNombre(), tp.getCaracter().getIdCaracter()+": "+tp.getCaracter().getNombre(), sexo, tp.getHabitad().getIdHabitad()+": "+tp.getHabitad().getNombre(), tp.getGrupoSanguineo(), pro);
        this.setTitle("SyncPet :: Actualizar Paciente");
        habilitarCampos();
    }
    
    public RegistroPaciente(Usuarios u, Mascota ma, Propietario pro, DetallePaciente buscar) {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarEspecie();
        rellenarCaracteres();
        rellenarSexo();
        rellenarHabitad();
        rellenarGrupoSangre();
        habilitarCampos();
        jpa = new MascotaJpaController(emf);
        jpb = new PropietarioJpaController(emf);
        this.tp = ma;
        this.tq = pro;
        String sexo;
        if(tp.getSexo() == 'M') {
            sexo = "Macho";
        }else if(tp.getSexo() == 'H') {
            sexo = "Hembra";
        }else{
            sexo = "Hermafrodita";
        }
        rellenarCampos(tp.getNombre(), tp.getFechaNacimiento(), tp.getRaza().getEspecie().getIdEspecie()+": "+tp.getRaza().getEspecie().getNombre(), tp.getRaza().getIdRaza()+": "+tp.getRaza().getNombre(), tp.getCaracter().getIdCaracter()+": "+tp.getCaracter().getNombre(), sexo, tp.getHabitad().getIdHabitad()+": "+tp.getHabitad().getNombre(), tp.getGrupoSanguineo(), pro);
        this.setTitle("SyncPet :: Actualizar Paciente");
        this.buscar = buscar;
        habilitarCampos();
    }
   
    
    //libreria básica de métodos para realizar tareas de CRUD de SyncPet
    
    public void rellenarEspecie() {
        for(Especie e : new EspecieJpaController(emf).findEspecieEntities()) {
            slcEspecie.addItem(e.getIdEspecie()+": "+e.getNombre());
        }
    }
    
    public void rellenarRaza() {
        slcRaza.removeAllItems();
        for(Raza r : new RazaJpaController(emf).buscarPorEspecie(new EspecieJpaController(emf).buscarPorNombre(hr.contenido(slcEspecie).split(": ")[1].trim()))) {
            slcRaza.addItem(r.getIdRaza()+": "+r.getNombre());
        }
    }
    
    public void rellenarCaracteres() {
        for(Caracter c : new CaracterJpaController(emf).findCaracterEntities()) {
            slcCaracter.addItem(c.getIdCaracter()+": "+c.getNombre());
        }
    }
    
    public void rellenarSexo() {
        slcSexo.addItem("Macho");
        slcSexo.addItem("Hembra");
        slcSexo.addItem("+Hermafrodita");
    }
    
    public void rellenarHabitad() {
        for(Habitad h : new HabitadJpaController(emf).findHabitadEntities()) {
            slcHabitad.addItem(h.getIdHabitad()+": "+h.getNombre());
        }
    }
    
    public void rellenarGrupoSangre() {
        slcGrupoSanguineo.addItem("DEA");
        slcGrupoSanguineo.addItem("A");
        slcGrupoSanguineo.addItem("AB");
        slcGrupoSanguineo.addItem("B");
        slcGrupoSanguineo.addItem("C");
        slcGrupoSanguineo.addItem("D");
        slcGrupoSanguineo.addItem("F");
        slcGrupoSanguineo.addItem("J");
        slcGrupoSanguineo.addItem("K");
        slcGrupoSanguineo.addItem("L");
        slcGrupoSanguineo.addItem("M");
        slcGrupoSanguineo.addItem("R");
        slcGrupoSanguineo.addItem("S");
        slcGrupoSanguineo.addItem("T");
        slcGrupoSanguineo.addItem("P");
        slcGrupoSanguineo.addItem("Q");
        slcGrupoSanguineo.addItem("U");
        slcGrupoSanguineo.addItem("Z");
    }
    
    public void vaciarCampos() {
        txtNombre.setText("");
        txtFechaNacimiento.setDate(null);
        slcEspecie.removeAllItems();
        slcRaza.removeAllItems();
        slcCaracter.removeAllItems();
        slcSexo.removeAllItems();
        slcHabitad.removeAllItems();
        slcGrupoSanguineo.removeAllItems();
        lblRutPropp.setText("-- indefinido --");
        lblInfoPropietario.setText("-- indefinido --");
        txtRut.setText("");
        this.tq = null;
        hr.insertarTexto(btnGuardarPaciente, "Guardar");
    }
    
    public void rellenarCampos(String nombre, Date fecha, String especie, String raza, String caracter, String sexo, String habitad, String grupo, Propietario pro) {
        vaciarCampos();
        txtNombre.setText(nombre);
        txtFechaNacimiento.setDate(fecha);
        slcEspecie.addItem(especie);
        rellenarEspecie();
        slcRaza.addItem(raza);
        rellenarRaza();
        slcCaracter.addItem(caracter);
        rellenarCaracteres();
        slcSexo.addItem(sexo);
        rellenarSexo();
        slcHabitad.addItem(habitad);
        rellenarHabitad();
        slcGrupoSanguineo.addItem(grupo);
        rellenarGrupoSangre();
        if(pro != null) {
            this.tq = pro;
            lblRutPropp.setText(pro.getRut()+"-"+pro.getDv());
            lblInfoPropietario.setText(pro.getNombres()+" "+pro.getApaterno()+" "+pro.getAmaterno());
            txtRut.setText(hrut.formatear(pro.getRut()+"-"+pro.getDv()));
        }
        hr.insertarTexto(btnGuardarPaciente, "Actualizar");
    }
    
    public void habilitarCampos() {
        txtNombre.setEnabled(true);
        txtFechaNacimiento.setEnabled(true);
        slcEspecie.setEnabled(true);
        slcRaza.setEnabled(true);
        slcCaracter.setEnabled(true);
        slcSexo.setEnabled(true);
        slcHabitad.setEnabled(true);
        slcGrupoSanguineo.setEnabled(true);
        txtNombre.requestFocus();
        txtRut.setEnabled(true);
    }
    
    public void deshabilitarCampos() {
        txtNombre.setEnabled(false);
        txtFechaNacimiento.setEnabled(false);
        slcEspecie.setEnabled(false);
        slcRaza.setEnabled(false);
        slcCaracter.setEnabled(false);
        slcSexo.setEnabled(false);
        slcHabitad.setEnabled(false);
        slcGrupoSanguineo.setEnabled(false);
        txtRut.setEnabled(false);
    }

    
    public void setRun(String run) {
        txtRut.setText(run);
        btnBuscarPropietarioActionPerformed(null);
    }
    // fin libreria básica
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatosPaciente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtFechaNacimiento = new com.toedter.calendar.JDateChooser();
        slcRaza = new javax.swing.JComboBox();
        slcEspecie = new javax.swing.JComboBox();
        slcCaracter = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        slcSexo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtChip = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        slcHabitad = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        slcGrupoSanguineo = new javax.swing.JComboBox();
        panelInfoPropietario = new javax.swing.JPanel();
        lblInfoPropietario = new javax.swing.JLabel();
        btnBuscarPropietario = new javax.swing.JButton();
        txtRut = new javax.swing.JTextField();
        lblRutPropp = new javax.swing.JLabel();
        btnCancelarRegistro = new javax.swing.JButton();
        btnGuardarPaciente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Registro de Paciente");
        setResizable(false);

        panelDatosPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Paciente"));

        jLabel1.setText("Nombre");

        jLabel2.setText("Raza");

        jLabel3.setText("Especie");

        jLabel5.setText("Caracter");

        jLabel6.setText("Fecha de Nacimiento");

        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        slcEspecie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                slcEspecieFocusLost(evt);
            }
        });
        slcEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slcEspecieActionPerformed(evt);
            }
        });

        jLabel8.setText("Sexo");

        jLabel10.setText("# Chip");

        txtChip.setText("0");
        txtChip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtChipKeyTyped(evt);
            }
        });

        jLabel11.setText("Habitad");

        jLabel15.setText("Grupo Sanguíneo");

        javax.swing.GroupLayout panelDatosPacienteLayout = new javax.swing.GroupLayout(panelDatosPaciente);
        panelDatosPaciente.setLayout(panelDatosPacienteLayout);
        panelDatosPacienteLayout.setHorizontalGroup(
            panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(67, 67, 67)
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slcRaza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slcEspecie, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)))
                    .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(61, 61, 61)
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                                .addComponent(slcSexo, 0, 83, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtChip, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(slcCaracter, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel11))
                        .addGap(21, 21, 21)
                        .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slcHabitad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slcGrupoSanguineo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelDatosPacienteLayout.setVerticalGroup(
            panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slcEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slcRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(slcCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(slcSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtChip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slcHabitad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(slcGrupoSanguineo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        panelInfoPropietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Propietario del Paciente")));

        lblInfoPropietario.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblInfoPropietario.setText("Propietario no definido");

        btnBuscarPropietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscarPropietario.setText("Buscar");
        btnBuscarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPropietarioActionPerformed(evt);
            }
        });

        txtRut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRutFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutFocusLost(evt);
            }
        });
        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });

        lblRutPropp.setText("Propietario no definido");

        javax.swing.GroupLayout panelInfoPropietarioLayout = new javax.swing.GroupLayout(panelInfoPropietario);
        panelInfoPropietario.setLayout(panelInfoPropietarioLayout);
        panelInfoPropietarioLayout.setHorizontalGroup(
            panelInfoPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPropietarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoPropietarioLayout.createSequentialGroup()
                        .addComponent(lblInfoPropietario)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoPropietarioLayout.createSequentialGroup()
                        .addComponent(txtRut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarPropietario))
                    .addGroup(panelInfoPropietarioLayout.createSequentialGroup()
                        .addComponent(lblRutPropp)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelInfoPropietarioLayout.setVerticalGroup(
            panelInfoPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPropietarioLayout.createSequentialGroup()
                .addGroup(panelInfoPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarPropietario)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoPropietario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRutPropp)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnCancelarRegistro.setText("Cancelar");
        btnCancelarRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarRegistroActionPerformed(evt);
            }
        });

        btnGuardarPaciente.setText("Guardar");
        btnGuardarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelInfoPropietario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelDatosPaciente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnGuardarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDatosPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfoPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarPaciente)
                    .addComponent(btnCancelarRegistro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPropietarioActionPerformed
        if(!hr.esVacio(txtRut)) {
            if(jpb.buscarPorRut(hr.contenido(txtRut).split("-")[0]) != null) {
                tq = jpb.buscarPorRut(hr.contenido(txtRut).split("-")[0]);
                hr.insertarTexto(lblInfoPropietario, tq.getNombres()+" "+tq.getApaterno()+" "+tq.getAmaterno());
                hr.insertarTexto(lblRutPropp, tq.getRut()+"-"+tq.getDv());
            }else{
                hr.mostrarError("El usuario con rut "+hr.contenido(txtRut)+" no esta registrado como propietario");
                hr.focus(txtRut);
                hr.desactivar(btnBuscarPropietario);
                hr.insertarTexto(lblInfoPropietario, "Propietario no definido");
                hr.insertarTexto(lblRutPropp, "Propietario no definido");
                tq = null;
            }
        }
    }//GEN-LAST:event_btnBuscarPropietarioActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        hr.largoMaximo(txtNombre, 75, evt);
        hr.ingresaSoloCaracteres(evt);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtChipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChipKeyTyped
        hr.ingresaSoloNumeros(evt);
        hr.largoMaximo(txtChip, 8, evt);
    }//GEN-LAST:event_txtChipKeyTyped

    private void slcEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slcEspecieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_slcEspecieActionPerformed

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost

    }//GEN-LAST:event_txtNombreFocusLost

    private void btnCancelarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarRegistroActionPerformed
        vaciarCampos();
        if(aat != null) {
            aat.setVisible(true);
        } 
        this.dispose();
        if(buscar != null) {
            buscar.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarRegistroActionPerformed

    private void slcEspecieFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slcEspecieFocusLost
        rellenarRaza();
    }//GEN-LAST:event_slcEspecieFocusLost

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
        hr.ingresaCaracteresRut(evt);
        hr.largoMaximo(txtRut, 12, evt);
    }//GEN-LAST:event_txtRutKeyTyped

    private void txtRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutFocusLost
        if(!hr.contenido(txtRut).isEmpty()) {
            txtRut.setText(hrut.formatear(hr.contenido(txtRut)));
            if(!hrut.validar(hr.contenido(txtRut))) {
                hr.mostrarError("El rut ingresado es erroneo");
                txtRut.requestFocus();
                hr.desactivar(btnBuscarPropietario);
            }else{
                hr.activar(btnBuscarPropietario);
                hr.focus(btnBuscarPropietario);
            }
        }
    }//GEN-LAST:event_txtRutFocusLost

    private void btnGuardarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPacienteActionPerformed
        if(hr.contenido(btnGuardarPaciente).compareToIgnoreCase("Guardar") == 0) {
            //crear
            if(!hr.esVacio(txtNombre)) {
                if(!hr.esVacio(txtFechaNacimiento)) {
                    if(!hr.esVacio(slcRaza)) {
                        if(!hr.esVacio(txtRut)) {
                            if(!hr.esVacio(txtChip)) {
                                if(hrut.validar(hr.contenido(txtRut))) {
                                    if(tq != null) {
                                        //crear el objeto
                                        try {
                                            tp = new Mascota();
                                            tp.setIdMascota(jpa.ultimo());
                                            tp.setNombre(hr.contenido(txtNombre));
                                            tp.setFechaNacimiento(hr.contenido(txtFechaNacimiento));
                                            tp.setSexo(hr.contenido(slcSexo).toUpperCase().charAt(0));
                                            tp.setNumeroChip(Integer.parseInt(hr.contenido(txtChip)));
                                            tp.setGrupoSanguineo(hr.contenido(slcGrupoSanguineo));
                                            tp.setPropietario(tq);
                                            tp.setRaza(new RazaJpaController(emf).findRaza(Integer.parseInt(hr.contenido(slcRaza).split(":")[0])));
                                            tp.setCaracter(new CaracterJpaController(emf).findCaracter(Integer.parseInt(hr.contenido(slcCaracter).split(":")[0])));
                                            tp.setHabitad(new HabitadJpaController(emf).findHabitad(Integer.parseInt(hr.contenido(slcHabitad).split(":")[0])));
                                            jpa.create(tp);
                                            hr.mostrarMensaje("El paciente "+hr.contenido(txtNombre)+" ha sido registrado.");
                                            vaciarCampos();
                                            if(aat == null) {
                                                this.dispose();
                                            }else{
                                                aat.vaciarPacientes();
                                                aat.rellenaPacientes();
                                                aat.actualizarProcesoRegistroPaciente();
                                                aat.setVisible(true);
                                                this.dispose();
                                            }
                                        }catch(Exception e) {
                                            hr.mostrarError("Ocurrió un error al intentar registrar el paciente: "+e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            //actualizar!
            if(!hr.esVacio(txtNombre)) {
                if(!hr.esVacio(txtFechaNacimiento)) {
                    if(!hr.esVacio(slcRaza)) {
                        if(!hr.esVacio(txtRut)) {
                            if(!hr.esVacio(txtChip)) {
                                if(hrut.validar(hr.contenido(txtRut))) {
                                    if(tq != null) {
                                        //actualizar el objeto
                                        try {
                                            tp.setIdMascota(tp.getIdMascota());
                                            tp.setNombre(hr.contenido(txtNombre));
                                            tp.setFechaNacimiento(hr.contenido(txtFechaNacimiento));
                                            tp.setSexo(hr.contenido(slcSexo).toUpperCase().charAt(0));
                                            tp.setNumeroChip(Integer.parseInt(hr.contenido(txtChip)));
                                            tp.setGrupoSanguineo(hr.contenido(slcGrupoSanguineo));
                                            tp.setPropietario(tq);
                                            tp.setRaza(new RazaJpaController(emf).findRaza(Integer.parseInt(hr.contenido(slcRaza).split(":")[0])));
                                            tp.setCaracter(new CaracterJpaController(emf).findCaracter(Integer.parseInt(hr.contenido(slcCaracter).split(":")[0])));
                                            tp.setHabitad(new HabitadJpaController(emf).findHabitad(Integer.parseInt(hr.contenido(slcHabitad).split(":")[0])));
                                            jpa.edit(tp);
                                            hr.mostrarMensaje("El paciente "+hr.contenido(txtNombre)+" ha sido actualizado.");
                                            vaciarCampos();
                                            this.dispose();
                                        }catch(Exception e) {
                                            hr.mostrarError("Ocurrió un error al intentar actualizar el paciente: "+e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(buscar != null) {
            buscar.actualizarDatos();
            buscar.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnGuardarPacienteActionPerformed

    private void txtRutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutFocusGained
        HerramientasRapidas.desactivar(btnBuscarPropietario);
    }//GEN-LAST:event_txtRutFocusGained


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistroPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarPropietario;
    private javax.swing.JButton btnCancelarRegistro;
    private javax.swing.JButton btnGuardarPaciente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblInfoPropietario;
    private javax.swing.JLabel lblRutPropp;
    private javax.swing.JPanel panelDatosPaciente;
    private javax.swing.JPanel panelInfoPropietario;
    private javax.swing.JComboBox slcCaracter;
    private javax.swing.JComboBox slcEspecie;
    private javax.swing.JComboBox slcGrupoSanguineo;
    private javax.swing.JComboBox slcHabitad;
    private javax.swing.JComboBox slcRaza;
    private javax.swing.JComboBox slcSexo;
    private javax.swing.JTextField txtChip;
    private com.toedter.calendar.JDateChooser txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
