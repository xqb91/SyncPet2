/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.veterinario;

import cl.starlabs.controladores.DetalleUsuariosJpaController;
import cl.starlabs.controladores.PerfilesJpaController;
import cl.starlabs.controladores.SucursalJpaController;
import cl.starlabs.controladores.UsuariosJpaController;
import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.modelo.Veterinario;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;

/**
 *
 * @author Janno
 */
public class RegistroVeterinario extends javax.swing.JFrame {

    EntityManagerFactory            emf = null;
    VeterinarioJpaController        jpa = null;
    SucursalJpaController           jpb = null;
    UsuariosJpaController           jpc = null;
    DetalleUsuariosJpaController    jpd = null;
    PerfilesJpaController           jpe = null;
    HerramientasRapidas             hr  = null;
    
    Veterinario                     vet = null;
    Usuarios                        usu = null;
    DetalleUsuarios                 det = null;
    Sucursal                        suc = null;
    Sucursal                        sax = null;
    
    public RegistroVeterinario() {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new VeterinarioJpaController(emf);
        jpb = new SucursalJpaController(emf);
        jpc = new UsuariosJpaController(emf);
        jpd = new DetalleUsuariosJpaController(emf);
        jpe = new PerfilesJpaController(emf);
        hr  = new HerramientasRapidas();
        this.suc = jpb.findSucursal(1);
        vaciarCampos();
        rellenarEspecialidad();
        rellenarSucursal();
        hr.focus(txtRut);
    }
    
    public RegistroVeterinario(Sucursal suc) {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new VeterinarioJpaController(emf);
        jpb = new SucursalJpaController(emf);
        jpc = new UsuariosJpaController(emf);
        jpd = new DetalleUsuariosJpaController(emf);
        jpe = new PerfilesJpaController(emf);
        this.suc = suc;
        hr  = new HerramientasRapidas();
        vaciarCampos();
        rellenarEspecialidad();
        rellenarSucursal();
    }
    
    public RegistroVeterinario(Sucursal suc, Veterinario vet) {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new VeterinarioJpaController(emf);
        jpb = new SucursalJpaController(emf);
        jpc = new UsuariosJpaController(emf);
        jpd = new DetalleUsuariosJpaController(emf);
        jpe = new PerfilesJpaController(emf);
        this.suc = suc;
        vaciarCampos();
        this.vet = vet;
        buscarDetalle();
        rellenarCampos(vet.getRut()+"-"+vet.getDv(), vet.getNombres(), vet.getApaterno(), vet.getAmaterno(), vet.getEspecialidad(), sax, usu);
        hr  = new HerramientasRapidas();
               
  
        rellenarEspecialidad();
        rellenarSucursal();
    }

    public void vaciarCampos() {
        hr.insertarTexto(txtRut, "");
        hr.insertarTexto(txtNombres, "");
        hr.insertarTexto(txtApaterno, "");
        hr.insertarTexto(txtAmaterno, "");
        hr.insertarTexto(txtUsuario, "");
        hr.insertarTexto(txtContraseña1, "");
        hr.insertarTexto(txtContraseña2, "");
        hr.insertarTexto(txtCorreoElectronico, "");
        
        cmbEspecialidad.removeAllItems();
        cmbSucursal.removeAllItems();
    }
    
    public void buscarDetalle() {
        if(vet != null) {
            //buscando valores de detalle
            for(DetalleUsuarios d : vet.getDetalleUsuariosList()) {
                if(d.getVeterinario().getIdVeterinario() == vet.getIdVeterinario()) {
                    det = d;
                    usu = d.getUsuario();
                    sax = d.getSucursal();
                    break;
                }
            }
        }
    }
    
    public void rellenarCampos(String rut, String nombres, String apaterno, String amaterno, String esp, Sucursal suc, Usuarios usu) {
        hr.insertarTexto(txtRut, rut);
        hr.insertarTexto(txtNombres, nombres);
        hr.insertarTexto(txtApaterno, apaterno);
        hr.insertarTexto(txtAmaterno, amaterno);
        hr.insertarTexto(txtUsuario, usu.getUsuario());
        hr.insertarTexto(txtContraseña1, usu.getContrasena());
        hr.insertarTexto(txtContraseña2, "");
        hr.insertarTexto(txtCorreoElectronico, usu.getCorreo());
        
        hr.insertarTexto(cmbEspecialidad, esp);
        hr.insertarTexto(cmbSucursal, suc.getIdSucursal()+": "+suc.getNombre());
        
        hr.insertarTexto(btnGuardar, "Actualizar");
    }
    
    public void rellenarEspecialidad() {
        hr.insertarTexto(cmbEspecialidad, "Medicina General");
        hr.insertarTexto(cmbEspecialidad, "Cirugía y Traumatologia");
        hr.insertarTexto(cmbEspecialidad, "Dermatología");
        hr.insertarTexto(cmbEspecialidad, "Ecografía");
        hr.insertarTexto(cmbEspecialidad, "Endoscopia");
        hr.insertarTexto(cmbEspecialidad, "Etología");
        hr.insertarTexto(cmbEspecialidad, "Fisioterapia");
        hr.insertarTexto(cmbEspecialidad, "Oncología");;
    }
    
    public void rellenarSucursal() {
        for(Sucursal s : jpb.buscarSucursales(suc.getClinica())) {
            hr.insertarTexto(cmbSucursal, s.getIdSucursal()+": "+s.getNombre());
        }
    }
    
    public void habilitarCampos() {
        hr.activar(txtRut);
        hr.activar(txtNombres);
        hr.activar(txtApaterno);
        hr.activar(txtAmaterno);
        hr.activar(cmbEspecialidad);
        hr.activar(cmbSucursal);
        hr.activar(txtUsuario);
        hr.activar(txtContraseña1);
        hr.activar(txtContraseña2);
        hr.activar(txtCorreoElectronico);
    }
    
    public void deshabilitarCampos() {
        hr.desactivar(txtRut);
        hr.desactivar(txtNombres);
        hr.desactivar(txtApaterno);
        hr.desactivar(txtAmaterno);
        hr.desactivar(cmbEspecialidad);
        hr.desactivar(cmbSucursal);
        hr.desactivar(txtUsuario);
        hr.desactivar(txtContraseña1);
        hr.desactivar(txtContraseña2);
        hr.desactivar(txtCorreoElectronico);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtApaterno = new javax.swing.JTextField();
        txtAmaterno = new javax.swing.JTextField();
        cmbEspecialidad = new javax.swing.JComboBox<String>();
        jLabel6 = new javax.swing.JLabel();
        cmbSucursal = new javax.swing.JComboBox<String>();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContraseña1 = new javax.swing.JPasswordField();
        txtContraseña2 = new javax.swing.JPasswordField();
        txtCorreoElectronico = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Registrar Veterinario");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Veterinario"));

        jLabel1.setText("Rut");

        jLabel2.setText("Nombres");

        jLabel3.setText("Apellido Paterno");

        jLabel4.setText("Apeliido Materno");

        jLabel5.setText("Especialidad");

        txtRut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutFocusLost(evt);
            }
        });
        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });

        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });

        txtApaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApaternoKeyTyped(evt);
            }
        });

        txtAmaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmaternoKeyTyped(evt);
            }
        });

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Medicina General", "Cirugía y Traumatologia", "Dermatología", "Ecografía", "Endoscopia ", "Etología", "Fisioterapia", "Oncología" }));

        jLabel6.setText("Sucursal");

        cmbSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRut)
                            .addComponent(txtNombres)
                            .addComponent(txtApaterno)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEspecialidad, 0, 166, Short.MAX_VALUE)
                            .addComponent(txtAmaterno)
                            .addComponent(cmbSucursal, 0, 166, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAmaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de inicio de sesion"));

        jLabel7.setText("Nombre de Usuario");

        jLabel8.setText("Contraseña");

        jLabel9.setText("Repetir Contraseña");

        jLabel10.setText("Correo electrónico");

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });

        txtContraseña1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseña1KeyTyped(evt);
            }
        });

        txtContraseña2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseña2KeyTyped(evt);
            }
        });

        txtCorreoElectronico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoElectronicoFocusLost(evt);
            }
        });
        txtCorreoElectronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoElectronicoActionPerformed(evt);
            }
        });
        txtCorreoElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoElectronicoKeyTyped(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/disk.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreoElectronico)
                            .addComponent(txtContraseña1)
                            .addComponent(txtUsuario)
                            .addComponent(txtContraseña2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtContraseña1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtContraseña2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
        hr.ingresaCaracteresRut(evt);
        hr.largoMaximo(txtRut, 12, evt);
    }//GEN-LAST:event_txtRutKeyTyped

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        hr.largoMaximo(txtNombres, 75, evt);
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtApaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApaternoKeyTyped
        hr.largoMaximo(txtApaterno, 75, evt);
    }//GEN-LAST:event_txtApaternoKeyTyped

    private void txtAmaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmaternoKeyTyped
        hr.largoMaximo(txtAmaterno, 75, evt);
    }//GEN-LAST:event_txtAmaternoKeyTyped

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        hr.largoMaximo(txtUsuario, 100, evt);
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtContraseña1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseña1KeyTyped
        hr.largoMaximo(txtContraseña1, 250, evt);
    }//GEN-LAST:event_txtContraseña1KeyTyped

    private void txtContraseña2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseña2KeyTyped
        hr.largoMaximo(txtContraseña2, 250, evt);
    }//GEN-LAST:event_txtContraseña2KeyTyped

    private void txtCorreoElectronicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoElectronicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoElectronicoActionPerformed

    private void txtCorreoElectronicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoElectronicoKeyTyped

    }//GEN-LAST:event_txtCorreoElectronicoKeyTyped

    private void txtRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutFocusLost
        if(!hr.contenido(txtRut).isEmpty()) {
            hr.insertarTexto(txtRut, cl.starlabs.herramientas.HerramientasRut.formatear(hr.contenido(txtRut)));
            if(!cl.starlabs.herramientas.HerramientasRut.validar(hr.contenido(txtRut))) {
                hr.mostrarError("Rut erroneo");
                hr.focus(txtRut);
            }
        }
    }//GEN-LAST:event_txtRutFocusLost

    private void txtCorreoElectronicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoElectronicoFocusLost
        if(!hr.contenido(txtCorreoElectronico).isEmpty()) {
            if(!cl.starlabs.herramientas.HerramientasCorreo.validarEmail(hr.contenido(txtCorreoElectronico))) {
                hr.mostrarError("Correo electrónico inválido");
                hr.focus(txtCorreoElectronico);
            }
        }
    }//GEN-LAST:event_txtCorreoElectronicoFocusLost

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(hr.contenido(btnGuardar).compareToIgnoreCase("Actualizar") != 0)
        {
            // --------- creacion -------------------
            if(!hr.esVacio(txtRut)){
                if(!hr.esVacio(txtNombres)) {
                    if(!hr.esVacio(txtApaterno)) {
                        if(!hr.esVacio(txtAmaterno)) {
                            if(!hr.esVacio(cmbEspecialidad)) {
                                if(!hr.esVacio(cmbSucursal)) {
                                    if(!hr.esVacio(txtUsuario)) {
                                        if(!hr.esVacio(txtContraseña1)) {
                                            if(!hr.esVacio(txtContraseña2)) {
                                                if(!hr.esVacio(txtCorreoElectronico)) {
                                                    if(hr.contenido(txtContraseña1).compareTo(hr.contenido(txtContraseña2)) == 0) {
                                                        //verificar que no exista otro usuario registrado con ese nombre
                                                        Usuarios aux = jpc.buscarUsuarioPorNickname(hr.contenido(txtUsuario));
                                                        if(aux == null){
                                                            
                                                            //creando veterinario
                                                            Veterinario vet = new Veterinario();
                                                            vet.setIdVeterinario(jpa.ultimo());
                                                            vet.setRut(hr.contenido(txtRut).replace(".", "").split("-")[0]);
                                                            vet.setDv(hr.contenido(txtRut).replace(".", "").split("-")[1].charAt(0));
                                                            vet.setNombres(hr.contenido(txtNombres));
                                                            vet.setApaterno(hr.contenido(txtApaterno));
                                                            vet.setAmaterno(hr.contenido(txtAmaterno));
                                                            vet.setEspecialidad(hr.contenido(cmbEspecialidad));
                                                            
                                                            //creando usuario
                                                            Usuarios usu = new Usuarios();
                                                            usu.setId(jpc.ultimo());
                                                            usu.setUsuario(hr.contenido(txtUsuario));
                                                            usu.setContrasena(hr.contenido(txtContraseña1));
                                                            usu.setNombres(hr.contenido(txtNombres));
                                                            usu.setApaterno(hr.contenido(txtApaterno));
                                                            usu.setAmaterno(hr.contenido(txtAmaterno));
                                                            usu.setCorreo(hr.contenido(txtCorreoElectronico));
                                                            usu.setBloqueado('0');
                                                            usu.setPerfil(jpe.buscarPerfilPor("Médico Veterinario"));
                                                            
                                                            //creando vinculo entre cuentas y tipos de registros
                                                            DetalleUsuarios det = new DetalleUsuarios();
                                                            det.setId(jpd.ultimo());
                                                            det.setUsuario(usu);
                                                            det.setVeterinario(vet);
                                                            det.setSucursal(jpb.findSucursal(Integer.parseInt(hr.contenido(cmbSucursal).split(":")[0])));
                                                            
                                                            //registrando todo!
                                                            try {
                                                                jpa.create(vet);
                                                                jpc.create(usu);
                                                                jpd.create(det);
                                                                hr.mostrarMensaje("Veterinario Registrado con éxito");
                                                                this.dispose();
                                                            } catch (Exception e) {
                                                                hr.mostrarError("Ocurrió un problema al intentar registrar el veterinario: "+e.getMessage());
                                                            }
                                                            
                                                        }else{
                                                            hr.mostrarError("El usuario "+aux.getUsuario()+" ya existe dentro del sistema");
                                                            hr.insertarTexto(txtUsuario, "");
                                                            hr.focus(txtUsuario);
                                                        }
                                                    }else{
                                                        hr.mostrarError("Las contraseñas no coinciden");
                                                        hr.insertarTexto(txtContraseña1, "");
                                                        hr.insertarTexto(txtContraseña2, "");
                                                        hr.focus(txtContraseña1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            // --------- actualizacion -------------------
            if(!hr.esVacio(txtRut)){
                if(!hr.esVacio(txtNombres)) {
                    if(!hr.esVacio(txtApaterno)) {
                        if(!hr.esVacio(txtAmaterno)) {
                            if(!hr.esVacio(cmbEspecialidad)) {
                                if(!hr.esVacio(cmbSucursal)) {
                                    if(!hr.esVacio(txtUsuario)) {
                                        if(!hr.esVacio(txtContraseña1)) {
                                            if(!hr.esVacio(txtContraseña2)) {
                                                if(!hr.esVacio(txtCorreoElectronico)) {
                                                    if(hr.contenido(txtContraseña1).compareTo(hr.contenido(txtContraseña2)) == 0) {
                                                        //verificar que no exista otro usuario registrado con ese nombre
                                                        Usuarios aux = jpc.buscarUsuarioPorNickname(hr.contenido(txtUsuario));
                                                        if(aux.getId() == usu.getId()){
                                                            
                                                            //creando veterinario
                                                            vet.setRut(hr.contenido(txtRut).replace(".", "").split("-")[0]);
                                                            vet.setDv(hr.contenido(txtRut).replace(".", "").split("-")[1].charAt(0));
                                                            vet.setNombres(hr.contenido(txtNombres));
                                                            vet.setApaterno(hr.contenido(txtApaterno));
                                                            vet.setAmaterno(hr.contenido(txtAmaterno));
                                                            vet.setEspecialidad(hr.contenido(cmbEspecialidad));
                                                            
                                                            //editando usuario
                                                            usu.setUsuario(hr.contenido(txtUsuario));
                                                            usu.setContrasena(hr.contenido(txtContraseña1));
                                                            usu.setNombres(hr.contenido(txtNombres));
                                                            usu.setApaterno(hr.contenido(txtApaterno));
                                                            usu.setAmaterno(hr.contenido(txtAmaterno));
                                                            usu.setCorreo(hr.contenido(txtCorreoElectronico));
                                                            usu.setBloqueado('0');
                                                            usu.setPerfil(jpe.buscarPerfilPor("Médico Veterinario"));
                                                            
                                                            //creando vinculo entre cuentas y tipos de registros
                                                            det.setUsuario(usu);
                                                            det.setVeterinario(vet);
                                                            det.setSucursal(jpb.findSucursal(Integer.parseInt(hr.contenido(cmbSucursal).split(":")[0])));
                                                            
                                                            //registrando todo!
                                                            try {
                                                                jpa.edit(vet);
                                                                jpc.edit(usu);
                                                                jpd.edit(det);
                                                                hr.mostrarMensaje("Veterinario Actualizado con éxito");
                                                                this.dispose();
                                                            } catch (Exception e) {
                                                                hr.mostrarError("Ocurrió un problema al intentar actualizar el veterinario: "+e.getMessage()+" | "+e.getLocalizedMessage());
                                                            }
                                                            
                                                        }else{
                                                            hr.mostrarError("El usuario "+aux.getUsuario()+" ya existe dentro del sistema");
                                                            hr.insertarTexto(txtUsuario, "");
                                                            hr.focus(txtUsuario);
                                                        }
                                                    }else{
                                                        hr.mostrarError("Las contraseñas no coinciden");
                                                        hr.insertarTexto(txtContraseña1, "");
                                                        hr.insertarTexto(txtContraseña2, "");
                                                        hr.focus(txtContraseña1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


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
            java.util.logging.Logger.getLogger(RegistroVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVeterinario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<String> cmbSucursal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtAmaterno;
    private javax.swing.JTextField txtApaterno;
    private javax.swing.JPasswordField txtContraseña1;
    private javax.swing.JPasswordField txtContraseña2;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
