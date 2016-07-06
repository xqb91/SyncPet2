/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.veterinario;

import cl.starlabs.controladores.DetalleUsuariosJpaController;
import cl.starlabs.controladores.UsuariosJpaController;
import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.herramientas.HerramientasTelefono;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Veterinario;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Janno
 */
public class DetalleVeterinario extends javax.swing.JFrame {

    Veterinario prop;
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasTelefono ht = new HerramientasTelefono();
    DetalleUsuariosJpaController jpb = null;
    UsuariosJpaController jpc = null;
    EntityManagerFactory emf = null;
    VeterinarioJpaController jpa = null;
    ListarVeterinario lp = null;
    
    public DetalleVeterinario() {
        initComponents();
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new VeterinarioJpaController(emf);
        this.jpb = new DetalleUsuariosJpaController(emf);
        this.jpc = new UsuariosJpaController(emf);
    }
    
    public DetalleVeterinario(Veterinario prop) {
        initComponents();
        this.prop = prop;
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new VeterinarioJpaController(emf);
        this.jpb = new DetalleUsuariosJpaController(emf);
        this.jpc = new UsuariosJpaController(emf);
        hr.insertarTexto(lblNombres, prop.getNombres());
        hr.insertarTexto(lblApellidos, prop.getApaterno()+" "+prop.getAmaterno());
        hr.insertarTexto(lblRut, prop.getRut()+"-"+prop.getDv());
        hr.insertarTexto(lblEspecialidad, prop.getEspecialidad());
        DetalleUsuarios det = null;
        for(DetalleUsuarios d : prop.getDetalleUsuariosList()) {
            if(d.getVeterinario().getIdVeterinario() == prop.getIdVeterinario()) {
                det = d;
                break;
            }
        }
        if(det != null) {
            hr.insertarTexto(lblNombreSuc, det.getSucursal().getNombre());
            hr.insertarTexto(lblTelefonoSuc, ht.formatearTelefono(det.getSucursal().getTelefono()+""));
            hr.insertarTexto(lblCorreoSuc, det.getSucursal().getEmail());
            hr.insertarTexto(lblDireccionSuc, det.getSucursal().getDireccion()+", "+det.getSucursal().getComuna().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getRegion().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getRegion().getPais().getNombre());
        }
    }
    
    public DetalleVeterinario(Veterinario prop, ListarVeterinario lp) {
        initComponents();
        this.prop = prop;
        this.lp = lp;
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new VeterinarioJpaController(emf);
        this.jpb = new DetalleUsuariosJpaController(emf);
        this.jpc = new UsuariosJpaController(emf);
        hr.insertarTexto(lblNombres, prop.getNombres());
        hr.insertarTexto(lblApellidos, prop.getApaterno()+" "+prop.getAmaterno());
        hr.insertarTexto(lblRut, prop.getRut()+"-"+prop.getDv());
        hr.insertarTexto(lblEspecialidad, prop.getEspecialidad());
        DetalleUsuarios det = null;
        for(DetalleUsuarios d : prop.getDetalleUsuariosList()) {
            if(d.getVeterinario().getIdVeterinario() == prop.getIdVeterinario()) {
                det = d;
                break;
            }
        }
        if(det != null) {
            hr.insertarTexto(lblNombreSuc, det.getSucursal().getNombre());
            hr.insertarTexto(lblTelefonoSuc, ht.formatearTelefono(det.getSucursal().getTelefono()+""));
            hr.insertarTexto(lblCorreoSuc, det.getSucursal().getEmail());
            hr.insertarTexto(lblDireccionSuc, det.getSucursal().getDireccion()+", "+det.getSucursal().getComuna().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getRegion().getNombre()+", "+det.getSucursal().getComuna().getProvincia().getRegion().getPais().getNombre());
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblRut = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblEspecialidad = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNombreSuc = new javax.swing.JLabel();
        lblTelefonoSuc = new javax.swing.JLabel();
        lblCorreoSuc = new javax.swing.JLabel();
        lblDireccionSuc = new javax.swing.JLabel();
        btnEditarVet = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Detalle Veterinario");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Veterinario"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Nombre Completo");

        lblNombres.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombres.setText("Nombres");

        lblApellidos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblApellidos.setText("Apellidos");

        lblRut.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblRut.setText("Rut");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Especialidad");

        lblEspecialidad.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblEspecialidad.setText("Especialidad no especificada");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblNombres)
                    .addComponent(lblApellidos)
                    .addComponent(lblRut)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblEspecialidad)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblApellidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblEspecialidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Sucursal"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Nombre");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Teléfono");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Correo");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Dirección");

        lblNombreSuc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombreSuc.setText("Nombre no especificado");

        lblTelefonoSuc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTelefonoSuc.setText("Telefono no especificado");

        lblCorreoSuc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblCorreoSuc.setText("Correo no especificado");

        lblDireccionSuc.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblDireccionSuc.setText("Direccion no especificada");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccionSuc)
                    .addComponent(lblCorreoSuc)
                    .addComponent(lblTelefonoSuc)
                    .addComponent(lblNombreSuc))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblNombreSuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTelefonoSuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblCorreoSuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblDireccionSuc))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEditarVet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_edit.png"))); // NOI18N
        btnEditarVet.setText("Editar Información del Veterinario");
        btnEditarVet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarVetActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_delete.png"))); // NOI18N
        btnEliminar.setText("Eliminar Veterinario");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditarVet, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarVet)
                    .addComponent(btnEliminar))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarVetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarVetActionPerformed
        new RegistroVeterinario(lp.getSucursal(), prop).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEditarVetActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if(hr.preguntar("¿Esta usted seguro de eliminar este veterinario?") == 0) {
            try {
                //recupenado el detalle segun ID del veterinario
                DetalleUsuarios aux = jpb.buscarPorVeterinario(prop);
                jpb.destroy(aux.getId());
                jpc.destroy(aux.getUsuario().getId());
                jpa.destroy(prop.getIdVeterinario());
                hr.mostrarMensaje("Veterinario Eliminado");
                if(lp != null) {
                    this.dispose();
                    lp.rellenar();
                    lp.setVisible(true);
                }
            } catch (Exception e) {
                hr.mostrarError("Ocurrió un problema mientras se intentaba eliminar el veterinario: "+e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

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
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DetalleVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetalleVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetalleVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetalleVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetalleVeterinario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarVet;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCorreoSuc;
    private javax.swing.JLabel lblDireccionSuc;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblNombreSuc;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblRut;
    private javax.swing.JLabel lblTelefonoSuc;
    // End of variables declaration//GEN-END:variables
}
