/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.propietario;

import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.modelo.Sucursal;
import javax.swing.UIManager;
import cl.starlabs.herramientas.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author Janno
 */
public class BuscarPropietario extends javax.swing.JFrame {

    Sucursal                    s  = null;
    HerramientasRapidas         hr = null;
    HerramientasCorreo          hc = null;
    HerramientasRut            hru = null;
    HerramientasTelefono        ht = null;
    EntityManagerFactory       emf = null;
    PropietarioJpaController   jpa = null;
    
    public BuscarPropietario() {
        initComponents();
        this.setLocationRelativeTo(null);
        lblNombreFiltro.setText("Seleccione filtro...");
        txtFiltro.setEnabled(false);
        btnFiltrar.setEnabled(false);
        this.hr = new HerramientasRapidas();
        this.hc = new HerramientasCorreo();
        this.hru = new HerramientasRut();
        this.ht = new HerramientasTelefono();
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new PropietarioJpaController(emf);
    }
    
    public BuscarPropietario(Sucursal s) {
        initComponents();
        this.setLocationRelativeTo(null);
        lblNombreFiltro.setText("Seleccione filtro...");
        txtFiltro.setEnabled(false);
        btnFiltrar.setEnabled(false);
        this.s = s;
        this.hr = new HerramientasRapidas();
        this.hc = new HerramientasCorreo();
        this.hru = new HerramientasRut();
        this.ht = new HerramientasTelefono();
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new PropietarioJpaController(emf);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFiltrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cmbCampoFiltro = new javax.swing.JComboBox<String>();
        txtFiltro = new javax.swing.JTextField();
        lblNombreFiltro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Buscar Propietario");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Propietario"));

        cmbCampoFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filtrar por...", "Run", "Nombres", "Apellido Paterno", "Apellido Materno", "Correo Electrónico", "Téfono Fijo", "Teléfono Celular" }));
        cmbCampoFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCampoFiltroItemStateChanged(evt);
            }
        });

        txtFiltro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFiltroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFiltroFocusLost(evt);
            }
        });
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCampoFiltro, 0, 348, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNombreFiltro)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtFiltro))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbCampoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombreFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnFiltrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCampoFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCampoFiltroItemStateChanged
        if(cmbCampoFiltro.getSelectedItem().toString().compareToIgnoreCase("Filtrar por...") != 0) {
            lblNombreFiltro.setText(cmbCampoFiltro.getSelectedItem().toString()+" del propietario:");
            txtFiltro.setEnabled(true);
            btnFiltrar.setEnabled(true);
            txtFiltro.requestFocus();         
        }else{
            lblNombreFiltro.setText("Seleccione filtro...");
            txtFiltro.setEnabled(false);
            btnFiltrar.setEnabled(false);
        }
    }//GEN-LAST:event_cmbCampoFiltroItemStateChanged

    private void txtFiltroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFiltroFocusGained
        
    }//GEN-LAST:event_txtFiltroFocusGained

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        switch(hr.contenido(cmbCampoFiltro)) {
            case "Run":
                hr.ingresaCaracteresRut(evt);
                hr.largoMaximo(txtFiltro, 12, evt);
                break;
            case "Nombres":
                hr.largoMaximo(txtFiltro, 75, evt);
                break;
            case "Apellido Paterno":
                hr.largoMaximo(txtFiltro, 75, evt);
                break;
            case "Apellido Materno":
                hr.largoMaximo(txtFiltro, 75, evt);
                break;
            case "Correo Electrónico":
                hr.largoMaximo(txtFiltro, 1000, evt);
                break;
            case "Téfono Fijo":
                hr.ingresaSoloNumeros(evt);
                hr.largoMaximo(txtFiltro, 12, evt);
                break;
            case "Teléfono Celular":
                hr.ingresaSoloNumeros(evt);
                hr.largoMaximo(txtFiltro, 12, evt);
                break;

            default:
                hr.largoMaximo(txtFiltro, 1000, evt);
                hr.insertarTexto(txtFiltro, "");
        }
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void txtFiltroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFiltroFocusLost
        switch(hr.contenido(cmbCampoFiltro)) {
            case "Run":
                if(!hr.contenido(txtFiltro).isEmpty()){
                    if(hru.validar(hr.contenido(txtFiltro))) {
                        hr.insertarTexto(txtFiltro, hru.formatear(hr.contenido(txtFiltro)));
                    }else{
                        hr.mostrarError("Rut inválido");
                        hr.insertarTexto(txtFiltro, "");
                        txtFiltro.requestFocus();
                    }
                }
                break;
            case "Téfono Fijo":
                if(!hr.contenido(txtFiltro).isEmpty()) {
                    if(ht.validarTelefono(hr.contenido(txtFiltro))) {
                        hr.insertarTexto(txtFiltro, ht.formatearTelefono(hr.contenido(txtFiltro)));
                    }else{
                        hr.mostrarError("Teléfono fijo erroneo");
                        hr.insertarTexto(txtFiltro, "");
                        txtFiltro.requestFocus();
                    }
                }
                break;
            case "Teléfono Celular":
                if(!hr.contenido(txtFiltro).isEmpty()) {
                    if(ht.validarCelular(hr.contenido(txtFiltro))) {
                        hr.insertarTexto(txtFiltro, ht.formatearCelular(hr.contenido(txtFiltro)));
                    }else{
                        hr.mostrarError("Teléfono celular erroneo");
                        hr.insertarTexto(txtFiltro, "");
                        txtFiltro.requestFocus();
                    }
                }
                break;
            
            default:
                
        }
    }//GEN-LAST:event_txtFiltroFocusLost

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        if(hr.contenido(cmbCampoFiltro).compareToIgnoreCase("Filtrar por...") != 0) {
            if(!hr.contenido(txtFiltro).isEmpty()) {
                new ListarPropietarios(s, hr.contenido(cmbCampoFiltro), hr.contenido(txtFiltro)).setVisible(true);
                this.dispose();
            }else{
                hr.mostrarError("Debe ingresar al menos un valor para realizar una búsqueda");
                hr.focus(txtFiltro);
            }
        }else{
            hr.mostrarError("Seleccione un filtro");
            hr.focus(cmbCampoFiltro);
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarPropietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JComboBox<String> cmbCampoFiltro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNombreFiltro;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
