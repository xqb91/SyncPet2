/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.paciente;

import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.herramientas.HerramientasCorreo;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.herramientas.HerramientasRut;
import cl.starlabs.herramientas.HerramientasTelefono;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Usuarios;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Manuel Araya
 */
public class BuscarPaciente extends javax.swing.JFrame {

    Propietario tp = null;
    Mascota tq = null;
    Usuarios us = null;
    EntityManagerFactory emf = null;
    PropietarioJpaController jpa;
    MascotaJpaController jpb;
    String rut;
    //definicion de herramientas
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasCorreo hc = new HerramientasCorreo();
    HerramientasRut hrut = new HerramientasRut();
    HerramientasTelefono ht = new HerramientasTelefono();
    
    //aplicaciones que usan esta aplicacion
    DetalleProgenitores det;
    String tipo;
    
    public BuscarPaciente() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        this.jpa = new PropietarioJpaController(emf);
        this.jpb = new MascotaJpaController(emf);
        rellenarTabla();
    }
    
    public BuscarPaciente(Usuarios us) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        this.jpa = new PropietarioJpaController(emf);
        this.jpb = new MascotaJpaController(emf);
        this.us = us;
        rellenarTabla();
    }
    
      public BuscarPaciente(DetalleProgenitores det, String tipo) {
        this.tipo = "";
        initComponents();
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.setLocationRelativeTo(null);
        this.jpa = new PropietarioJpaController(emf);
        this.jpb = new MascotaJpaController(emf);
        this.det = det;
        this.tipo = tipo;
        rellenarTabla();
    }  

    public void rellenarTabla() {
        if(!hr.contenido(txtRut).isEmpty()) {
            vaciarTabla();
            DefaultTableModel modelo = new DefaultTableModel(new Object [][][] { }, new String [] { "ID", "Nombre", "Fecha Nacimiento" });
            tablaResultados.getColumnModel().getColumn(0).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setResizable(false);
            tablaResultados.getColumnModel().getColumn(2).setResizable(false);
            for(Mascota te : jpa.buscarPorRut(hr.contenido(txtRut).split("-")[0]).getMascotaList()) {
                Object[] obj = new Object[3];
                obj[0] = te.getIdMascota();
                obj[1] = te.getNombre();
                obj[2] = te.getFechaNacimiento();
                modelo.addRow(obj);
            }
            tablaResultados.setModel(modelo);
        }
    }
    
    public void actualizarTabla() {
        if(!hr.contenido(txtRut).isEmpty()) {
            vaciarTabla();
            DefaultTableModel modelo = new DefaultTableModel(new Object [][][] { }, new String [] { "ID", "Nombre", "Fecha Nacimiento" });
            tablaResultados.getColumnModel().getColumn(0).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setResizable(false);
            tablaResultados.getColumnModel().getColumn(2).setResizable(false);
            for(Mascota te : jpa.buscarPorRut(this.rut).getMascotaList()) {
                Object[] obj = new Object[3];
                obj[0] = te.getIdMascota();
                obj[1] = te.getNombre();
                obj[2] = te.getFechaNacimiento();
                modelo.addRow(obj);
            }
            tablaResultados.setModel(modelo);
        }
    }
    
    public void vaciarCampos() {
        hr.insertarTexto(txtRut, "");
    }
    
    public void vaciarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][][] { }, new String [] { "ID", "Nombre", "Fecha Nacimiento" });
        tablaResultados.getColumnModel().getColumn(0).setResizable(false);
        tablaResultados.getColumnModel().getColumn(1).setResizable(false);
        tablaResultados.getColumnModel().getColumn(2).setResizable(false);
        tablaResultados.setModel(modelo);
    }
    
    public void rellenarCampos(String rut) {
        vaciarCampos();
        hr.insertarTexto(txtRut, rut);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Buscar Paciente");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar paciente por propietario"));

        jLabel2.setText("Propietario");

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

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Fecha de Nacimiento"
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
        tablaResultados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaResultadosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaResultados);
        if (tablaResultados.getColumnModel().getColumnCount() > 0) {
            tablaResultados.getColumnModel().getColumn(0).setResizable(false);
            tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaResultados.getColumnModel().getColumn(1).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(45);
            tablaResultados.getColumnModel().getColumn(2).setResizable(false);
            tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(60);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                hr.desactivar(btnBuscar);
            }else{
                hr.activar(btnBuscar);
                hr.focus(btnBuscar);
            }
        }
    }//GEN-LAST:event_txtRutFocusLost

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if(!hr.esVacio(txtRut)) {
            if(jpa.buscarPorRut(hr.contenido(txtRut).split("-")[0]) != null) {
                rellenarTabla();
            }else{
                hr.mostrarError("El usuario con rut "+hr.contenido(txtRut)+" no esta registrado como propietario");
                hr.focus(txtRut);
                tablaResultados.setEnabled(false);
                tp = null;
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        vaciarCampos();
        vaciarTabla();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        if(tipo == null && det == null) {
            DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
            //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
            tq = jpb.findMascota(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
            if(tq == null) {
                hr.mostrarError("El paciente no pudo ser hallado por el sistema");
            }else{
                //new RegistroPaciente(us, tq, tq.getPropietario()).setVisible(true);
                new DetallePaciente(tq, this).setVisible(true);
                this.rut = hr.contenido(txtRut);
                this.dispose();
            }
        }else{
            if(tipo.compareToIgnoreCase("paciente") == 0) {
                DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
                //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
                tq = jpb.findMascota(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
                det.setPaciente(tq);
                det.setVisible(true);
                this.dispose();
            }else if(tipo.compareToIgnoreCase("padre") == 0) {
                DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
                //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
                tq = jpb.findMascota(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
                det.setPadre(tq);
                det.setVisible(true);
                this.dispose();
            }else if(tipo.compareToIgnoreCase("madre") == 0){
                DefaultTableModel modelo = (DefaultTableModel)tablaResultados.getModel();
                //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
                tq = jpb.findMascota(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaResultados.getSelectedRow(), 0))));
                det.setMadre(tq);
                det.setVisible(true);
                this.dispose();
            }
        }
    }//GEN-LAST:event_tablaResultadosMouseClicked

    private void tablaResultadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaResultadosKeyPressed

    }//GEN-LAST:event_tablaResultadosKeyPressed

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
            java.util.logging.Logger.getLogger(BuscarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
