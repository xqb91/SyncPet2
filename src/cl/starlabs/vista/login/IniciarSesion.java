/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.login;

import cl.starlabs.controladores.SucursalJpaController;
import cl.starlabs.controladores.UsuariosJpaController;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.vista.principal.PrincipalAdmin;
import cl.starlabs.vista.principal.PrincipalMedico;
import cl.starlabs.vista.principal.PrincipalRecepcionista;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Victor Manuel Araya
 */
public class IniciarSesion extends javax.swing.JFrame {

    EntityManagerFactory emf = null;
    Usuarios us = null;
    public IniciarSesion() {
        initComponents();
        //centrando ventana
        this.setLocationRelativeTo(null);
        //colocando icono a ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_renovado.png"));
        setIconImage(icon);
        setVisible(true);
        
        emf = Persistence.createEntityManagerFactory("SyncPetPU");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        panelLogin = new javax.swing.JPanel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblSucursal = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        slcSucursal = new javax.swing.JComboBox();
        btnIniciarSesion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        menuLogin = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        ArchivoEstadoServer = new javax.swing.JMenuItem();
        ArchivoSalir = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        ayudaAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StarLabs SyncPet");
        setName("frmLogin"); // NOI18N
        setResizable(false);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/logo aplicacion slide.png"))); // NOI18N

        panelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Iniciar Sesión"));

        lblNombreUsuario.setText("Nombre Usuario");

        lblPassword.setText("Contraseña");

        lblSucursal.setText("Sucursal");

        txtNombreUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreUsuarioFocusLost(evt);
            }
        });
        txtNombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreUsuarioActionPerformed(evt);
            }
        });
        txtNombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUsuarioKeyTyped(evt);
            }
        });

        slcSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        slcSucursal.setEnabled(false);
        slcSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                slcSucursalItemStateChanged(evt);
            }
        });

        btnIniciarSesion.setText("Iniciar Sesión");
        btnIniciarSesion.setEnabled(false);
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtPassword.setMaximumSize(new java.awt.Dimension(5, 5));
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreUsuario)
                    .addComponent(lblPassword)
                    .addComponent(lblSucursal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(slcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreUsuario)
                            .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPassword)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSucursal)
                    .addComponent(slcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuArchivo.setText("Archivo");

        ArchivoEstadoServer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        ArchivoEstadoServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/server.png"))); // NOI18N
        ArchivoEstadoServer.setText("Estado del Servidor");
        menuArchivo.add(ArchivoEstadoServer);

        ArchivoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        ArchivoSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/door_out.png"))); // NOI18N
        ArchivoSalir.setText("Salir");
        menuArchivo.add(ArchivoSalir);

        menuLogin.add(menuArchivo);

        menuAyuda.setText("Ayuda");

        ayudaAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        ayudaAcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/information.png"))); // NOI18N
        ayudaAcercaDe.setText("Acerca de SyncPet");
        menuAyuda.add(ayudaAcercaDe);

        menuLogin.add(menuAyuda);

        setJMenuBar(menuLogin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtNombreUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreUsuarioFocusLost

    }//GEN-LAST:event_txtNombreUsuarioFocusLost

    private void txtNombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuarioActionPerformed

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {                                      
        if((!txtNombreUsuario.getText().isEmpty()) && (!txtPassword.getText().isEmpty())) {
            //consultado por valores válidos del usuario
            us = new UsuariosJpaController(emf).buscarUsuarioPorNickname(txtNombreUsuario.getText());
            if(us == null) {
                //usuario no existe
                JOptionPane.showMessageDialog(null, "El usuario no existe");
                txtNombreUsuario.setText("");
                txtPassword.setText("");
                txtNombreUsuario.requestFocus();
            }else{
                if(us.getContrasena().compareToIgnoreCase(txtPassword.getText()) == 0) {
                    //usuario correcto y password correcta
                    //habilitando campo de seleccion de sucursal
                    slcSucursal.setEnabled(true);
                    if(!(slcSucursal.getItemCount() >= 2)) {
                        for(DetalleUsuarios s : us.getDetalleUsuariosList()) {
                            slcSucursal.addItem(s.getSucursal().getIdSucursal()+": "+s.getSucursal().getNombre());
                        }
                    }
                    slcSucursal.requestFocus();
                }else{
                    //usuario correcto y password incorrecta
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    txtPassword.requestFocus();
                    txtPassword.selectAll();
                }
            }
        }
    }                                     

    private void slcSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_slcSucursalItemStateChanged
        if(slcSucursal.getSelectedItem().toString().compareToIgnoreCase("Seleccione...") != 0) {
            btnIniciarSesion.setEnabled(true);
            btnIniciarSesion.requestFocus();
        }else{
            btnIniciarSesion.setEnabled(false);
            slcSucursal.requestFocus();
        }
    }//GEN-LAST:event_slcSucursalItemStateChanged

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
        if(us == null) {
            JOptionPane.showMessageDialog(null, "Debe especificar sus datos para iniciar sesión");
            initComponents();
        }else{
            if(us.getPerfil().getPerfil().compareToIgnoreCase("Administrador") == 0) {
                Sucursal s = new SucursalJpaController(emf).findSucursal(Integer.parseInt(slcSucursal.getSelectedItem().toString().split(":")[0]));
                new PrincipalAdmin(us, s).setVisible(true);
                this.dispose();
            }else{
                if(us.getPerfil().getPerfil().compareToIgnoreCase("Médico Veterinario") == 0) {
                    Sucursal s = new SucursalJpaController(emf).findSucursal(Integer.parseInt(slcSucursal.getSelectedItem().toString().split(":")[0]));
                    new PrincipalMedico(us, s).setVisible(true);
                    this.dispose();
                }else{
                    if(us.getPerfil().getPerfil().compareToIgnoreCase("Recepcionista") == 0) {
                        Sucursal s = new SucursalJpaController(emf).findSucursal(Integer.parseInt(slcSucursal.getSelectedItem().toString().split(":")[0]));
                        new PrincipalRecepcionista(us, s).setVisible(true);
                        this.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "El perfil especificado no existe... consulte a StarLabs");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void txtNombreUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUsuarioKeyTyped
        if(txtNombreUsuario.getText().length() >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreUsuarioKeyTyped

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
        if(txtPassword.getText().length() >= 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPasswordKeyTyped

    /**
     * @param args the command line arguments
     */
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
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IniciarSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ArchivoEstadoServer;
    private javax.swing.JMenuItem ArchivoSalir;
    private javax.swing.JMenuItem ayudaAcercaDe;
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSucursal;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuBar menuLogin;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JComboBox slcSucursal;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
