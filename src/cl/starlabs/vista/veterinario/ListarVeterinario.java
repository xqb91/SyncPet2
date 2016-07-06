/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.veterinario;

import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.modelo.Veterinario;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janno
 */
public class ListarVeterinario extends javax.swing.JFrame {

    Sucursal                        s = null;
    String                      campo = "";
    String                     filtro = "";
    EntityManagerFactory          emf = null;
    VeterinarioJpaController      jpa = null;
    HerramientasRapidas            hr = null;
    
    public ListarVeterinario() {
        initComponents();
    }
    
    public ListarVeterinario(Sucursal s) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new VeterinarioJpaController(emf);
        this.s = s;
        this.hr = new HerramientasRapidas();
        rellenar();
    } 
    
    public ListarVeterinario(Sucursal s, String campo, String filtro) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.s = s;
        this.campo = campo;
        this.filtro = filtro;
        this.emf = Persistence.createEntityManagerFactory("SyncPetPU");
        this.jpa = new VeterinarioJpaController(emf);
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
         DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "ID", "RUN", "Nombre", "Usuario" });
         if((!campo.isEmpty()) && (!filtro.isEmpty())) {
            for(Veterinario p : jpa.buscarMultiple(campo, filtro)) {
                Object[] obj = new Object[4];
                obj[0] = p.getIdVeterinario();
                obj[1] = p.getRut()+"-"+p.getDv();
                obj[2] = p.getNombres()+" "+p.getApaterno()+" "+p.getAmaterno();
                Usuarios aux = null;
                for(DetalleUsuarios d : p.getDetalleUsuariosList()) {
                    if(d.getVeterinario().getIdVeterinario() == p.getIdVeterinario()) {
                        aux = d.getUsuario();
                        break;
                    }
                }
                if(aux == null) {
                    obj[3] = "Sin Especificar";
                }else{
                    obj[3] = aux.getUsuario();
                }
                modelo.addRow(obj);
            } 
         }else{
            for(Veterinario p : jpa.findVeterinarioEntities()) {
                Object[] obj = new Object[4];
                obj[0] = p.getIdVeterinario();
                obj[1] = p.getRut()+"-"+p.getDv();
                obj[2] = p.getNombres()+" "+p.getApaterno()+" "+p.getAmaterno();
                Usuarios aux = null;
                for(DetalleUsuarios d : p.getDetalleUsuariosList()) {
                    if(d.getVeterinario().getIdVeterinario() == p.getIdVeterinario()) {
                        aux = d.getUsuario();
                        break;
                    }
                }
                if(aux == null) {
                    obj[3] = "Sin Especificar";
                }else{
                    obj[3] = aux.getUsuario();
                }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Listar Veterinario");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Veterinarios Registrados"));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUT", "Nombre", "Usuario"
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addContainerGap())
        );

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        Veterinario m = jpa.findVeterinario(Integer.parseInt(hr.retornaValorTabla(0, tabla)));
        if(m == null) {
            hr.mostrarError("El veterinario no pudo ser encontrado");
        }else{
            new DetalleVeterinario(m, this).setVisible(true);
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
            java.util.logging.Logger.getLogger(ListarVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarVeterinario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarVeterinario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
