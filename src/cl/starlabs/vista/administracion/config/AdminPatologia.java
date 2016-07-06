/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.administracion.config;

import cl.starlabs.controladores.TipoPatologiaJpaController;
import cl.starlabs.modelo.TipoPatologia;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Manuel Araya
 */
public class AdminPatologia extends javax.swing.JFrame {

    TipoPatologia tp = null;
    EntityManagerFactory emf = null;
    TipoPatologiaJpaController jpa = null;
    
    public AdminPatologia() {
        initComponents();
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        rellenarTabla();
        deshabilitarCampos();
        jpa = new TipoPatologiaJpaController(emf);
    }

    
    //libreria básica de métodos para realizar tareas de CRUD de SyncPet
    public void rellenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "ID", "Patologia" });
        tablaResultados.getColumnModel().getColumn(0).setResizable(false);
        tablaResultados.getColumnModel().getColumn(1).setResizable(false);
        for(TipoPatologia te : new TipoPatologiaJpaController(emf).findTipoPatologiaEntities()) {
            Object[] obj = new Object[2];
            obj[0] = te.getIdTipoPatologia();
            obj[1] = te.getNombrePatologia();
            modelo.addRow(obj);
        }
        tablaResultados.setModel(modelo);
    }
    
    public void vaciarCampos() {
        txtNombre.setText("");
        txtEtiologia.setText("");
        txtPatogenia.setText("");
        txtMorfología.setText("");
    }
    
    public void rellenarCampos(String nombre, String etiologia, String patogenia, String morfologia) {
        vaciarCampos();
        txtNombre.setText(nombre);
        txtEtiologia.setText(etiologia);
        txtPatogenia.setText(patogenia);
        txtMorfología.setText(morfologia);
    }
    
    public void habilitarCampos() {
        txtNombre.setEnabled(true);
        txtEtiologia.setEnabled(true);
        txtPatogenia.setEnabled(true);
        txtMorfología.setEnabled(true);
        btnAccion.setEnabled(true);
        btnCancelar.setEnabled(true);
        tablaResultados.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnRemover.setEnabled(true);
        txtNombre.requestFocus();
    }
    
    public void deshabilitarCampos() {
        txtNombre.setEnabled(false);
        txtEtiologia.setEnabled(false);
        txtPatogenia.setEnabled(false);
        txtMorfología.setEnabled(false);
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
    
    public String contenido(JTextField obj) {
        return obj.getText();
    }
    
    public void largoMaximo(JTextField obj ,Integer largo, java.awt.event.KeyEvent evt) {
        if(contenido(obj).length() > (largo-1)) {
            evt.consume();
        }
    }
    // fin libreria básica
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblDescripcion = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        txtMorfología = new javax.swing.JTextField();
        txtEtiologia = new javax.swing.JTextField();
        lblPatogenia = new javax.swing.JLabel();
        txtPatogenia = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Administrar Patologías");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades"));

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        lblNombre.setText("Nombre de la patología");

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

        lblDescripcion.setText("Etiología");

        lblValor.setText("Morfología");

        lblPatogenia.setText("Patogenia");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMorfología)
                    .addComponent(txtEtiologia)
                    .addComponent(txtPatogenia)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescripcion)
                            .addComponent(lblPatogenia)
                            .addComponent(lblValor))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDescripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEtiologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPatogenia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPatogenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMorfología, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Patologías"));

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Patología"
            }
        ));
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaResultadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaResultados);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnRemover)))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // eventos programables de botones
    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
        //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
        tp = jpa.findTipoPatologia(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
        if(tp == null) {
            this.mostrarError("El tipo de patologia no pudo ser hallado por el sistema");
        }else{
            habilitarCampos();
            rellenarCampos(tp.getNombrePatologia(), tp.getEtiologia(), tp.getPatogenia(), tp.getMorfologia());
            btnAccion.setText("Actualizar");
        }
    }//GEN-LAST:event_tablaResultadosMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        habilitarCampos();
        btnRemover.setEnabled(false);
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            if(!esVacio(txtNombre)) {
                if(!esVacio(txtEtiologia)) {
                    if(!esVacio(txtPatogenia)) {
                        if(!esVacio(txtMorfología)) {
                            if(jpa.existeTipo(txtNombre.getText())) {
                                this.mostrarError("El tipo de examen "+txtNombre.getText()+" ya se encuentra registrado en la base de datos");
                                txtNombre.selectAll();
                                txtNombre.requestFocus();
                            }else{
                                try {
                                    jpa.create(new TipoPatologia(jpa.ultimo(), contenido(txtNombre), contenido(txtEtiologia), contenido(txtPatogenia), contenido(txtMorfología)));
                                    mostrarMensaje("Guardado");
                                    btnCancelarActionPerformed(evt);
                                }catch(Exception e) {
                                    mostrarError("Ocurrió un error al intentar registrar el tipo de examen en el sistema: "+e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }else{
            //update
            if(!esVacio(txtNombre)) {
                if(!esVacio(txtEtiologia)) {
                    if(!esVacio(txtPatogenia)) {
                        if(!esVacio(txtMorfología)) {
                            if(jpa.existeTipo(txtNombre.getText())) {
                                try {
                                    if(jpa.buscarPorNombre(contenido(txtNombre)) == null) {
                                        tp.setNombrePatologia(contenido(txtNombre));
                                        tp.setEtiologia(contenido(txtEtiologia));
                                        tp.setPatogenia(contenido(txtPatogenia));
                                        tp.setMorfologia(contenido(txtMorfología));
                                        jpa.edit(tp);
                                        mostrarMensaje("Actualizado");
                                        tp = null;
                                        btnCancelarActionPerformed(evt);
                                    }else{
                                        if(jpa.buscarPorNombre(contenido(txtNombre)).getIdTipoPatologia()== tp.getIdTipoPatologia()) {
                                            tp.setNombrePatologia(contenido(txtNombre));
                                            tp.setEtiologia(contenido(txtEtiologia));
                                            tp.setPatogenia(contenido(txtPatogenia));
                                            tp.setMorfologia(contenido(txtMorfología));
                                            jpa.edit(tp);
                                            mostrarMensaje("Actualizado");
                                            tp = null;
                                            btnCancelarActionPerformed(evt);
                                        }else{
                                            mostrarError("El tipo de examen ya se encuentra registrado en la base de datos");
                                            txtNombre.selectAll();
                                            txtNombre.requestFocus();
                                        }
                                    }
                                }catch(Exception e) {
                                    mostrarError("Ha ocurrido un error al intentar actualizar el tipo de examen: "+e.getMessage());
                                }
                            }else{
                                try {
                                    tp.setNombrePatologia(contenido(txtNombre));
                                    tp.setEtiologia(contenido(txtEtiologia));
                                    tp.setPatogenia(contenido(txtPatogenia));
                                    tp.setMorfologia(contenido(txtMorfología));
                                    jpa.edit(tp);
                                    mostrarMensaje("Actualizado");
                                    tp = null;
                                    btnCancelarActionPerformed(evt);
                                }catch(Exception e) {
                                    mostrarError("Ha ocurrido un error al intentar actualizar el tipo de examen: "+e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
        //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
        if(jpa.findTipoPatologia(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0)))) != null) {
            int opcion = preguntar("¿Esta seguro de eliminar el tipo de patologia?");
            if(opcion == 0) {
                try {
                    jpa.destroy(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
                    mostrarMensaje("Eliminado");
                    btnCancelarActionPerformed(evt);
                }catch(Exception e) {
                    mostrarError("No se pudo encontrar el tipo de examen en la base de datos porque ya no existe");
                    btnCancelarActionPerformed(evt);
                }
            }
        }else{
            mostrarError("No se pudo encontrar el tipo de examen en la base de datos porque ya no existe");
            btnCancelarActionPerformed(evt);          
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        largoMaximo(txtNombre, 248, evt);
    }//GEN-LAST:event_txtNombreKeyTyped
    //fin programables

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
            java.util.logging.Logger.getLogger(AdminPatologia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPatologia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPatologia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPatologia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPatologia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPatogenia;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtEtiologia;
    private javax.swing.JTextField txtMorfología;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPatogenia;
    // End of variables declaration//GEN-END:variables
}
