package cl.starlabs.vista.agenda;

import cl.starlabs.controladores.AgendaDetalleJpaController;
import cl.starlabs.controladores.AgendaJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.herramientas.HerramientasRut;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.AgendaDetalle;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janno
 */
public class BuscarAtencion extends javax.swing.JFrame {

    Agenda tp;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SyncPetPU");
    AgendaJpaController jpa = new AgendaJpaController(emf);
    AgendaDetalleJpaController jpb = new AgendaDetalleJpaController(emf);
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasRut hh = new HerramientasRut();
    
    public BuscarAtencion() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    
    public void deshabilitarCampos() {
        btnBuscar.setEnabled(false);
        tablaResultados.setEnabled(false);
    }
    
    public void habilitarCampos() {
        btnBuscar.setEnabled(true);
        tablaResultados.setEnabled(true);
    }
    
    public void rellenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "Evento", "Fecha", "Paciente", "Propietario" });
        tablaResultados.getColumnModel().getColumn(0).setResizable(false);
        tablaResultados.getColumnModel().getColumn(1).setResizable(false);
        tablaResultados.getColumnModel().getColumn(2).setResizable(false);
        tablaResultados.getColumnModel().getColumn(3).setResizable(false); 
        
        if(jpb.buscarPorPropietario(hr.contenido(txtRutPropietario)) != null) {
            for(Agenda te : jpb.buscarPorPropietario(hr.contenido(txtRutPropietario))) {
                Object[] obj = new Object[4];
                obj[0] = te.getIdEvento();
                obj[1] = new SimpleDateFormat("dd-MMMMM-yyyy HH:mm:ss").format(te.getFechaEvento()).replace(" ", " a las ").replace("-", " de ");
                obj[2] = te.getAgendaDetalleList().get(0).getMascota().getNombre();
                obj[3] = te.getAgendaDetalleList().get(0).getMascota().getPropietario().getNombres().split(" ")[0]+" "+te.getAgendaDetalleList().get(0).getMascota().getPropietario().getApaterno();
                modelo.addRow(obj);
            }
        }
        tablaResultados.setModel(modelo);
        tablaResultados.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaResultados.getColumnModel().getColumn(0).setMinWidth(45);
        tablaResultados.getColumnModel().getColumn(0).setWidth(45);
        tablaResultados.getColumnModel().getColumn(1).setMaxWidth(200);
        tablaResultados.getColumnModel().getColumn(1).setMinWidth(200);
        tablaResultados.getColumnModel().getColumn(1).setWidth(200);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtRutPropietario = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Buscar Atencion");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Atencion"));

        jLabel1.setText("Rut propietario");

        txtRutPropietario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutPropietarioFocusLost(evt);
            }
        });
        txtRutPropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutPropietarioKeyTyped(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Evento", "Fecha", "Paciente", "Propietario"
            }
        ));
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaResultadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaResultados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtRutPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtRutPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtRutPropietarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutPropietarioFocusLost
        if(!hr.contenido(txtRutPropietario).isEmpty() && hr.contenido(txtRutPropietario).length() > 1) {
            //formatear campo
            hr.insertarTexto(txtRutPropietario, hh.formatear(hr.contenido(txtRutPropietario)));
            
            //verificar rut
            if(hh.validar(hr.contenido(txtRutPropietario))) {
                habilitarCampos();
                hr.focus(btnBuscar);
                txtRutPropietario.setForeground(Color.black);
            }else{
                txtRutPropietario.setForeground(Color.red);
                txtRutPropietario.selectAll();
                hr.focus(btnBuscar);
            }
        }else{
            txtRutPropietario.setForeground(Color.red);
            txtRutPropietario.selectAll();
            hr.focus(btnBuscar);
        }
    }//GEN-LAST:event_txtRutPropietarioFocusLost

    private void txtRutPropietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutPropietarioKeyTyped
        hr.ingresaCaracteresRut(evt);
        hr.largoMaximo(txtRutPropietario, 12, evt);
    }//GEN-LAST:event_txtRutPropietarioKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if(hr.contenido(txtRutPropietario).isEmpty()) {
            hr.focus(txtRutPropietario);
        }else{
            if(hh.validar(hr.contenido(txtRutPropietario))) {
                //acciones
                rellenarTabla();
            }else{
                hr.mostrarError("Rut invalido");
                txtRutPropietario.selectAll();
                hr.focus(txtRutPropietario);
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        //descoponiendo & obteniendo identificador de agenda
        String identificador = hr.retornaValorTabla(0, tablaResultados);
        identificador = identificador.split("]")[0].replace("[", "").trim();
        //obteniendo detalles del evento
        try{
            Agenda aux = new AgendaJpaController(emf).findAgenda(Integer.parseInt(identificador));
            if(aux == null) {
                hr.mostrarError("El evento con identificador "+identificador+" ya no esta disponible en la base de datos");
            }else{
                if(hr.preguntar("¿Desea ver el detalle del evento del "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(aux.getFechaEvento()).replace(" ", " a las ").replace("-", " de ")+" para el paciente "+aux.getAgendaDetalleList().get(0).getMascota().getNombre()+"?") == 0) {
                    new DetalleEvento(aux).setVisible(true);
                }
            }
        }catch(Exception e) {
            hr.mostrarError("No se pudo encontrar el evento: "+e.getMessage());
        }
    }//GEN-LAST:event_tablaResultadosMouseClicked

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
            java.util.logging.Logger.getLogger(BuscarAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarAtencion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtRutPropietario;
    // End of variables declaration//GEN-END:variables
}
