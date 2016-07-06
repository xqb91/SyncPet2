/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.administracion.trabajadores;

import cl.starlabs.controladores.DetalleUsuariosJpaController;
import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.PerfilesJpaController;
import cl.starlabs.controladores.SucursalJpaController;
import cl.starlabs.controladores.UsuariosJpaController;
import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.modelo.Clinica;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Perfiles;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.modelo.Veterinario;
import java.awt.Color;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cetecom
 */
public class AdministradorTrabajadores extends javax.swing.JFrame  {

    EntityManagerFactory emf = null;
    Sucursal suc = null;
    Usuarios tp = null;
    UsuariosJpaController jpa;
    
    public AdministradorTrabajadores() {
        initComponents();
    }
    
    public AdministradorTrabajadores(Sucursal suc) {
        initComponents();
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        this.jpa = new UsuariosJpaController(emf);
        rellenarTabla();
        deshabilitarCampos();
        this.suc = suc;
        this.setTitle("SyncPet :: Administrador de Trabajadores -- "+suc.getClinica().getNombreReal()+" --");       
    }

    //libreria básica de métodos para realizar tareas de CRUD de SyncPet
    public void rellenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "ID", "Usuario" });
        tablaResultados.getColumnModel().getColumn(0).setResizable(false);
        tablaResultados.getColumnModel().getColumn(1).setResizable(false);
        for(Usuarios te : jpa.findUsuariosEntities()) {
            Object[] obj = new Object[2];
            obj[0] = te.getId();
            obj[1] = te.getUsuario();
            modelo.addRow(obj);
        }
        tablaResultados.setModel(modelo);
    }
    
    public void vaciarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtNombres.setText("");
        txtApaterno.setText("");
        txtAmaterno.setText("");
        txtCorreo.setText("");
        cmbEstado.removeAllItems();
        cmbTipoUsuario.removeAllItems();
    }
    
    public void rellenarCombos(){
        cmbEstado.addItem("Habilitado");
        cmbEstado.addItem("Deshabilitado");
        for(Perfiles p : new PerfilesJpaController(emf).findPerfilesEntities()) {
            if(p.getPerfil().compareToIgnoreCase("Médico Veterinario") != 0) {
                cmbTipoUsuario.addItem(p.getId()+": "+p.getPerfil());
            }
        }
    }
    
    public void rellenarCampos(String usuario, String contrasena, String nombres, String apaterno, String amaterno, String correo, String estado, String tipo) {
        vaciarCampos();
        txtUsuario.setText(usuario);
        txtContrasena.setText(contrasena);
        txtNombres.setText(nombres);
        txtApaterno.setText(apaterno);
        txtAmaterno.setText(amaterno);
        txtCorreo.setText(correo);
        cmbEstado.addItem(estado);
        cmbTipoUsuario.addItem(tipo);
        rellenarCombos();
    }
        
    public void habilitarCampos() {
        txtUsuario.setEnabled(true);
        txtContrasena.setEnabled(true);
        txtNombres.setEnabled(true);
        txtApaterno.setEnabled(true);
        txtAmaterno.setEnabled(true);
        txtCorreo.setEnabled(true);
        cmbEstado.setEnabled(true);
        cmbTipoUsuario.setEnabled(true);
        btnAccion.setEnabled(true);
        btnCancelar.setEnabled(true);
        tablaResultados.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnRemover.setEnabled(true);
        txtUsuario.requestFocus();
        rellenarCombos();
    }
    
    public void deshabilitarCampos() {
        txtUsuario.setEnabled(false);
        txtContrasena.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApaterno.setEnabled(false);
        txtAmaterno.setEnabled(false);
        txtCorreo.setEnabled(false);
        cmbEstado.setEnabled(false);
        cmbTipoUsuario.setEnabled(false);
        btnAccion.setEnabled(false);
        btnCancelar.setEnabled(false);
        tablaResultados.setEnabled(true);
        btnAgregar.setEnabled(true);
        btnRemover.setEnabled(false);
        tablaResultados.requestFocus();
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "SyncPet", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public Integer preguntar(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "SyncPet", JOptionPane.YES_NO_OPTION);
    }
    
    public boolean esVacio(JTextField obj) {
        if(obj.getText().isEmpty()){
            this.mostrarError("Hay un campo vacio, por favor rellenelo");
            obj.requestFocus();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean esVacio(JTextArea obj) {
        if(obj.getText().isEmpty()){
            this.mostrarError("Hay un campo vacio, por favor rellenelo");
            obj.requestFocus();
            return true;
        }else{
            return false;
        }
    }
    
    public String contenido(JTextField obj) {
        return obj.getText();
    }
    
    public String contenido(JPasswordField obj) {
        return obj.getText();
    }
    
    public String contenido(JTextArea obj) {
        return obj.getText();
    }
    
    public Integer contenidoInt(JTextField obj) throws Exception {
        try {
            return Integer.parseInt(obj.getText());
        } catch (Exception e) {
            mostrarError("Las letras en un campo numérico no pueden ser convertidos en números");
            obj.selectAll();
            obj.requestFocus();
            return 0;
        }
    }
    
    public void largoMaximo(JTextField obj ,Integer largo, java.awt.event.KeyEvent evt) {
        if(contenido(obj).length() > (largo-1)) {
            evt.consume();
        }
    }
    
    public String contenido(JComboBox obj) {
        return obj.getSelectedItem().toString();
    }
    
    public Integer contenidoInt(JComboBox obj) {
        try {
            return Integer.parseInt(contenido(obj).split(":")[0]);
        } catch (Exception e) {
            mostrarError("No se pudo transformar el valor seleccionado a integer");
            obj.requestFocus();
            return 0;
        }
    }
    
        public Integer primerValorCombo(JComboBox obj) {
        try {
            return Integer.parseInt(contenido(obj).split(":")[0]);
        } catch (Exception e) {
            mostrarError("No se pudo transformar el valor seleccionado a integer");
            obj.requestFocus();
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblClinica = new javax.swing.JLabel();
        lblNombreClinica = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        panelInfoCuenta = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblNombres = new javax.swing.JLabel();
        lblApaterno = new javax.swing.JLabel();
        lblAmaterno = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtApaterno = new javax.swing.JTextField();
        txtAmaterno = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        cmbEstado = new javax.swing.JComboBox<String>();
        btnAccion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTipoUsuario = new javax.swing.JLabel();
        cmbTipoUsuario = new javax.swing.JComboBox();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Administrador de Trabajadores");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuentas de Usuario de clínica"));

        lblClinica.setText("Clínica");

        lblNombreClinica.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNombreClinica.setText("Nombre de la clínica");

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuario", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaResultadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaResultados);
        if (tablaResultados.getColumnModel().getColumnCount() > 0) {
            tablaResultados.getColumnModel().getColumn(0).setResizable(false);
            tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(10);
            tablaResultados.getColumnModel().getColumn(1).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(25);
            tablaResultados.getColumnModel().getColumn(2).setResizable(false);
        }

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/add.png"))); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/delete.png"))); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClinica)
                    .addComponent(lblNombreClinica))
                .addContainerGap(103, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblClinica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreClinica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRemover, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        panelInfoCuenta.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de la Cuenta"));

        lblUsuario.setText("Usuario");

        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });

        lblPassword.setText("Contraseña");

        txtContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContrasenaFocusLost(evt);
            }
        });
        txtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyTyped(evt);
            }
        });

        lblNombres.setText("Nombres");

        lblApaterno.setText("Apellido Paterno");

        lblAmaterno.setText("Apellido Materno");

        lblCorreo.setText("Correo Electrónico");

        lblEstado.setText("Estado de Cuenta");

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

        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoFocusLost(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Habilitado", "Deshabilitado" }));
        cmbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEstadoItemStateChanged(evt);
            }
        });
        cmbEstado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbEstadoFocusLost(evt);
            }
        });
        cmbEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbEstadoMouseClicked(evt);
            }
        });

        btnAccion.setText("Guardar");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblTipoUsuario.setText("Tipo Usuario");

        cmbTipoUsuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoUsuarioItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelInfoCuentaLayout = new javax.swing.GroupLayout(panelInfoCuenta);
        panelInfoCuenta.setLayout(panelInfoCuentaLayout);
        panelInfoCuentaLayout.setHorizontalGroup(
            panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblPassword)
                    .addComponent(lblNombres)
                    .addComponent(lblApaterno)
                    .addComponent(lblAmaterno)
                    .addComponent(lblCorreo)
                    .addComponent(lblEstado)
                    .addComponent(lblTipoUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtApaterno, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombres, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAmaterno)
                    .addComponent(txtCorreo)
                    .addComponent(cmbEstado, 0, 178, Short.MAX_VALUE)
                    .addComponent(cmbTipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelInfoCuentaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        panelInfoCuentaLayout.setVerticalGroup(
            panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoCuentaLayout.createSequentialGroup()
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombres)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApaterno)
                    .addComponent(txtApaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmaterno)
                    .addComponent(txtAmaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreo)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoUsuario))
                .addGap(49, 49, 49)
                .addGroup(panelInfoCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        habilitarCampos();
        btnRemover.setEnabled(false);       
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
        //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
        tp = jpa.findUsuarios(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
        if(tp == null) {
            this.mostrarError("El tipo de fármaco no pudo ser hallado por el sistema");
        }else{
            habilitarCampos();
            String habilitado;
            if(tp.getBloqueado() == '0'){
                habilitado = "Habilitado";
            }else{
                habilitado = "Deshabilitado";
            }
            rellenarCampos(tp.getUsuario(), tp.getContrasena(), tp.getNombres(), tp.getApaterno(), tp.getAmaterno(), tp.getCorreo(), habilitado, tp.getPerfil().getId()+": "+tp.getPerfil().getPerfil());
            btnAccion.setText("Actualizar");
        }
    }//GEN-LAST:event_tablaResultadosMouseClicked

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
        //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
        if(jpa.findUsuarios(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0)))) != null) {
            int opcion = preguntar("¿Esta seguro de eliminar el usuario "+contenido(txtUsuario)+"?");
            if(opcion == 0) {
                try {
                    jpa.destroy(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
                    mostrarMensaje("Eliminado");
                    btnCancelarActionPerformed(evt);
                }catch(Exception e) {
                    mostrarError("No se pudo encontrar el usuario en la base de datos porque ya no existe");
                    btnCancelarActionPerformed(evt);
                }
            }
        }else{
            mostrarError("No se pudo encontrar el usuario en la base de datos porque ya no existe");
            btnCancelarActionPerformed(evt);          
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void cmbTipoUsuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoUsuarioItemStateChanged

    }//GEN-LAST:event_cmbTipoUsuarioItemStateChanged

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        vaciarCampos();
        deshabilitarCampos();
        rellenarTabla();
        tp = null;
        btnAccion.setText("Guardar");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        if(btnAccion.getText().compareToIgnoreCase("guardar") == 0) {
            // NUEVO
            if(!esVacio(txtUsuario)) {
                if(!esVacio(txtContrasena)) {
                    if(!esVacio(txtNombres)) {
                        if(!esVacio(txtApaterno)) {
                            if(!esVacio(txtAmaterno)) {
                                if(!esVacio(txtCorreo)) {
                                    if(jpa.buscarUsuarioPorNickname(contenido(txtUsuario)) != null) {
                                        this.mostrarError("El usuario "+contenido(txtUsuario)+" ya se encuentra registrado en la base de datos");
                                        txtUsuario.selectAll();
                                        txtUsuario.requestFocus();
                                    }else{
                                        try {
                                            char c;
                                            if(contenido(cmbEstado).compareToIgnoreCase("Habilitado") == 0)
                                                c = '0';
                                            else
                                                c = '1';
                                            
                                            Perfiles p = new PerfilesJpaController(emf).findPerfiles(contenidoInt(cmbTipoUsuario));
                                            jpa.create(new Usuarios(jpa.ultimo(),contenido(txtUsuario), contenido(txtContrasena), contenido(txtNombres), contenido(txtApaterno), contenido(txtAmaterno), contenido(txtCorreo), c, p));
                                            mostrarMensaje("Usuario guardado");
                                            btnCancelarActionPerformed(evt);
                                        }catch(Exception e) {
                                            mostrarError("Ocurrió un error al intentar registrar el usuario en el sistema: "+e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            //update
            if(!esVacio(txtUsuario)) {
                if(!esVacio(txtContrasena)) {
                    if(!esVacio(txtNombres)) {
                        if(!esVacio(txtApaterno)) {
                            if(!esVacio(txtAmaterno)) {
                                if(!esVacio(txtCorreo)) {
                                    if(jpa.buscarUsuarioPorNickname(contenido(txtUsuario)) != null) {
                                        try {
                                            if(jpa.buscarUsuarioPorNickname(contenido(txtUsuario)) == null) {
                                                tp.setUsuario(contenido(txtUsuario));
                                                tp.setContrasena(contenido(txtContrasena));
                                                tp.setNombres(contenido(txtNombres));
                                                tp.setApaterno(contenido(txtApaterno));
                                                tp.setAmaterno(contenido(txtAmaterno));
                                                tp.setCorreo(contenido(txtCorreo));
                                                char c;
                                                if(contenido(cmbEstado).compareToIgnoreCase("Habilitado") == 0)
                                                    c = '0';
                                                else
                                                c = '1';
                                                tp.setBloqueado(c);
                                                Perfiles p = new PerfilesJpaController(emf).findPerfiles(contenidoInt(cmbTipoUsuario));
                                                tp.setPerfil(p);
                                                jpa.edit(tp);
                                                mostrarMensaje("Actualizado");
                                                tp = null;
                                                btnCancelarActionPerformed(evt);
                                            }else{
                                                if(jpa.buscarUsuarioPorNickname(contenido(txtUsuario)).getId()== tp.getId()) {
                                                    tp.setUsuario(contenido(txtUsuario));
                                                    tp.setContrasena(contenido(txtContrasena));
                                                    tp.setNombres(contenido(txtNombres));
                                                    tp.setApaterno(contenido(txtApaterno));
                                                    tp.setAmaterno(contenido(txtAmaterno));
                                                    tp.setCorreo(contenido(txtCorreo));
                                                    char c;
                                                    if(contenido(cmbEstado).compareToIgnoreCase("Habilitado") == 0)
                                                        c = '0';
                                                    else
                                                    c = '1';
                                                    tp.setBloqueado(c);
                                                    Perfiles p = new PerfilesJpaController(emf).findPerfiles(contenidoInt(cmbTipoUsuario));
                                                    tp.setPerfil(p);
                                                    jpa.edit(tp);
                                                    mostrarMensaje("Actualizado");
                                                    tp = null;
                                                    btnCancelarActionPerformed(evt);
                                                }else{
                                                    mostrarError("El usuario ya se encuentra registrado en la base de datos");
                                                    txtUsuario.selectAll();
                                                    txtUsuario.requestFocus();
                                                }
                                            }
                                        }catch(Exception e) {
                                            mostrarError("Ha ocurrido un error al intentar actualizar el usuario: "+e.getMessage());
                                        }
                                    }else{
                                        try {
                                            tp.setUsuario(contenido(txtUsuario));
                                            tp.setContrasena(contenido(txtContrasena));
                                            tp.setNombres(contenido(txtNombres));
                                            tp.setApaterno(contenido(txtApaterno));
                                            tp.setAmaterno(contenido(txtAmaterno));
                                            tp.setCorreo(contenido(txtCorreo));
                                            char c;
                                            if(contenido(cmbEstado).compareToIgnoreCase("Habilitado") == 0)
                                                c = '0';
                                            else
                                            c = '1';
                                            tp.setBloqueado(c);
                                            Perfiles p = new PerfilesJpaController(emf).findPerfiles(contenidoInt(cmbTipoUsuario));
                                            tp.setPerfil(p);
                                            jpa.edit(tp);
                                            mostrarMensaje("Actualizado");
                                            tp = null;
                                            btnCancelarActionPerformed(evt);
                                        }catch(Exception e) {
                                            mostrarError("Ha ocurrido un error al intentar actualizar el usuario: "+e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    private void cmbEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbEstadoMouseClicked

    }//GEN-LAST:event_cmbEstadoMouseClicked

    private void cmbEstadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbEstadoFocusLost

    }//GEN-LAST:event_cmbEstadoFocusLost

    private void cmbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEstadoItemStateChanged

    }//GEN-LAST:event_cmbEstadoItemStateChanged

    private void txtCorreoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusLost

    }//GEN-LAST:event_txtCorreoFocusLost

    private void txtAmaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmaternoKeyTyped

    }//GEN-LAST:event_txtAmaternoKeyTyped

    private void txtApaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApaternoKeyTyped

    }//GEN-LAST:event_txtApaternoKeyTyped

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped

    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtContrasenaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaKeyTyped

    }//GEN-LAST:event_txtContrasenaKeyTyped

    private void txtContrasenaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContrasenaFocusLost

    }//GEN-LAST:event_txtContrasenaFocusLost

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped

    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost

    }//GEN-LAST:event_txtUsuarioFocusLost


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
            java.util.logging.Logger.getLogger(AdministradorTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministradorTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministradorTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministradorTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministradorTrabajadores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox cmbTipoUsuario;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAmaterno;
    private javax.swing.JLabel lblApaterno;
    private javax.swing.JLabel lblClinica;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombreClinica;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTipoUsuario;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelInfoCuenta;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtAmaterno;
    private javax.swing.JTextField txtApaterno;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
