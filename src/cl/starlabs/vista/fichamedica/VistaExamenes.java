/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.fichamedica;

import cl.starlabs.controladores.ExamenesJpaController;
import cl.starlabs.controladores.HospitalizacionJpaController;
import cl.starlabs.controladores.TipoExamenJpaController;
import cl.starlabs.herramientas.HerramientasFTP;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.Examenes;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Veterinario;
import cl.starlabs.modelo.TipoExamen;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Janno
 */
public class VistaExamenes extends javax.swing.JFrame {

    Mascota m = null;
    Veterinario v = null;
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasFTP hftp = new HerramientasFTP();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SyncPetPU");
    JFileChooser dlg = null;
    
    public VistaExamenes() {
        initComponents();
        this.setLocationRelativeTo(null);
        rellenarTipoExamen();
    }
    public VistaExamenes(Mascota mascota, Veterinario veterinario) {
        this.setLocationRelativeTo(null);
        initComponents();
        this.m = mascota;
        this.v = veterinario;
        hr.insertarTexto(lblMascota, m.getNombre());
        hr.insertarTexto(lblVeterinario, v.getNombres());
        rellenarTipoExamen();
    }
    
    public void rellenarTipoExamen()
    {
        cmbTipoExamen.removeAllItems();
        for(TipoExamen te : new TipoExamenJpaController(emf).findTipoExamenEntities())
        {
            hr.insertarTexto(cmbTipoExamen,te.getIdTipoExamen()+": "+te.getNombreExamen());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoExamen = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaObservaciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaResultado = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblNombreArchivo = new javax.swing.JLabel();
        lblMascota = new javax.swing.JLabel();
        lblVeterinario = new javax.swing.JLabel();
        btnBuscarArchivo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Examenes");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Examenes"));

        jLabel1.setText("Tipo de Examen");

        jLabel2.setText("Observaciones");

        jLabel3.setText("Resultado");

        jLabel4.setText("Archivo");

        cmbTipoExamen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar..." }));

        textAreaObservaciones.setColumns(20);
        textAreaObservaciones.setRows(5);
        jScrollPane1.setViewportView(textAreaObservaciones);

        textAreaResultado.setColumns(20);
        textAreaResultado.setRows(5);
        jScrollPane2.setViewportView(textAreaResultado);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Mascota");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Veterinario");

        lblNombreArchivo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombreArchivo.setText("Recuperacion de archivo no especificada");

        lblMascota.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblMascota.setText("Mascota no especificada");

        lblVeterinario.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblVeterinario.setText("Veterinario no especificado");

        btnBuscarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/attach.png"))); // NOI18N
        btnBuscarArchivo.setText("Archivo");
        btnBuscarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNombreArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(btnBuscarArchivo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)
                                .addComponent(cmbTipoExamen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblMascota)
                            .addComponent(lblVeterinario))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbTipoExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblNombreArchivo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblMascota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblVeterinario)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarArchivo)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarArchivoActionPerformed
        try
        {  
            dlg = new JFileChooser(); //Crea un objeto de dialogo JFileChooser
            int option = dlg.showOpenDialog(this); //Abre la ventana de dialogo
            if(option == JFileChooser.APPROVE_OPTION)// Si hace click en el boton abrir del dialogo 
            {
                String ruta = dlg.getSelectedFile().getPath(); // .getPath()obtiene ruta y nombre del archivo seleccionado
                // Si solo quieres el nombre del archivo, debe usar getName()
                if(ruta.length()>45)
                {
                    lblNombreArchivo.setText(ruta.substring(0, 45)+"..."); // Muestra nombre de archivo
                }else
                {
                    lblNombreArchivo.setText(ruta);
                }
            }      
        }catch(Exception e){
            hr.mostrarError("Ha ocurrido un error: "+e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarArchivoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        System.gc();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Examenes exam = new Examenes();
        try {
            //Recuperando datos
            if(!hr.esVacio(textAreaResultado))
            {
                TipoExamen te = new TipoExamenJpaController(emf).findTipoExamen(Integer.parseInt(hr.contenido(cmbTipoExamen).split(":")[0]));
                
                exam.setIdExamen(new ExamenesJpaController(emf).ultimo());
                exam.setFechaSolicitud(new GregorianCalendar().getTime());
                exam.setTipoExamen(te);
                exam.setObservacion(textAreaObservaciones.getText());
                exam.setResultado(textAreaResultado.getText());
                exam.setArchivo(lblNombreArchivo.getText());
                exam.setMascota(m);
                exam.setVeterinario(v);
                exam.setHospitalizacion(new HospitalizacionJpaController(emf).findHospitalizacion(1));
                
                new ExamenesJpaController(emf).create(exam);
                hr.mostrarMensaje("Examen registrado exitosamente");
                btnCancelarActionPerformed(evt);
            }
        } catch (Exception e) {
            hr.mostrarError("Se produjo un error "+e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(VistaExamenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaExamenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaExamenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaExamenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaExamenes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarArchivo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbTipoExamen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMascota;
    private javax.swing.JLabel lblNombreArchivo;
    private javax.swing.JLabel lblVeterinario;
    private javax.swing.JTextArea textAreaObservaciones;
    private javax.swing.JTextArea textAreaResultado;
    // End of variables declaration//GEN-END:variables
}
