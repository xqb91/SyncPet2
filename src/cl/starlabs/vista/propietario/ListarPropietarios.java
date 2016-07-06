/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.propietario;

import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Sucursal;
import java.awt.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janno
 */
public class ListarPropietarios extends javax.swing.JFrame {

   
    Sucursal s = null;
    String campo = "";
    String filtro = "";
    EntityManagerFactory emf = null;
    PropietarioJpaController jpa = null;
    HerramientasRapidas hr = null;
    
    public ListarPropietarios() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public ListarPropietarios(Sucursal s) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new PropietarioJpaController(emf);
        this.s = s;
        this.hr = new HerramientasRapidas();
        rellenar();
    }
    
     public ListarPropietarios(Sucursal s, String campo, String filtro) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.s = s;
        this.campo = campo;
        this.filtro = filtro;
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new PropietarioJpaController(emf);
        this.hr = new HerramientasRapidas();
        rellenar();
    }   
     
     
    public Sucursal getSucursal() {
        return s;
    }

    public void setSucursal(Sucursal s) {
        this.s = s;
    }

     public void rellenar(){
         DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "ID", "RUN", "Nombre", "# Pacientes" });
         if((!campo.isEmpty()) && (!filtro.isEmpty())) {
            for(Propietario p : jpa.buscarMultiple(campo, filtro)) {
                Object[] obj = new Object[4];
                obj[0] = p.getIdPropietario();
                obj[1] = p.getRut()+"-"+p.getDv();
                obj[2] = p.getNombres()+" "+p.getApaterno()+" "+p.getAmaterno();
                obj[3] = p.getMascotaList().size()+" Pacientes";
                modelo.addRow(obj);
            } 
         }else{
            for(Propietario p : jpa.buscarPorClinica(s.getClinica())) {
                Object[] obj = new Object[4];
                obj[0] = p.getIdPropietario();
                obj[1] = p.getRut()+"-"+p.getDv();
                obj[2] = p.getNombres()+" "+p.getApaterno()+" "+p.getAmaterno();
                obj[3] = p.getMascotaList().size()+" Pacientes";
                modelo.addRow(obj);
            } 
         }
         tabla.setModel(modelo);
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        registrar = new javax.swing.JMenuItem();
        buscar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Propietarios del Sistema");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Propietarios Registrados"));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUN", "NOMBRE", "# Pacientes"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setText("Acciones");

        registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_add.png"))); // NOI18N
        registrar.setText("Registrar Propietario");
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });
        jMenu1.add(registrar);

        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        buscar.setText("Buscar Propietario");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        jMenu1.add(buscar);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        new RegistroPropietario(s).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registrarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        new BuscarPropietario(s).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buscarActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        Propietario m = jpa.findPropietario(Integer.parseInt(hr.retornaValorTabla(0, tabla)));
        if(m == null) {
            hr.mostrarError("El propietario no pudo ser encontrado");
        }else{
            new DetallesPropietario(m, this).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_tablaMouseClicked

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
            java.util.logging.Logger.getLogger(ListarPropietarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarPropietarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarPropietarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarPropietarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarPropietarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem buscar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem registrar;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
