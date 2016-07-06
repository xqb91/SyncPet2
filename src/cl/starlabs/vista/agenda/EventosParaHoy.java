/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.agenda;

import cl.starlabs.controladores.AgendaJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.Agenda;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janno
 */
public class EventosParaHoy extends javax.swing.JFrame {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SyncPetPU");
    AgendaJpaController jpb = new AgendaJpaController(emf);
    HerramientasRapidas hr = new HerramientasRapidas();
    
    public EventosParaHoy() {
        initComponents();
        this.setLocationRelativeTo(null);
        rellenarEventosDefault();
    }

    public void rellenarEventosDefault() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "Evento", "Paciente", "Propietario", "Hora" });
        Calendar inicio = new GregorianCalendar();
        inicio.set(Calendar.HOUR_OF_DAY, 0);
        inicio.set(Calendar.MINUTE, 00);
        inicio.set(Calendar.SECOND, 00);
        inicio.set(Calendar.MILLISECOND, 000);
        Calendar finali = new GregorianCalendar();
        finali.set(Calendar.HOUR_OF_DAY, 23);
        finali.set(Calendar.MINUTE, 59);
        finali.set(Calendar.SECOND, 59);
        finali.set(Calendar.MILLISECOND, 999);
        
        for(Agenda a : jpb.eventosPorFecha(inicio.getTime(), finali.getTime())) {
            Object[] obj = new Object[4];
            obj[0] = a.getIdEvento();
            obj[1] = a.getAgendaDetalleList().get(0).getMascota().getNombre();
            obj[2] = a.getAgendaDetalleList().get(0).getMascota().getPropietario().getNombres().split(" ")[0]+" "+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getApaterno();
            obj[3] = new SimpleDateFormat("HH:mm").format(a.getFechaEvento());
            modelo.addRow(obj);
        }
        //arreglar este método
        
        tablaEventosParaHoy.setModel(modelo);
        tablaEventosParaHoy.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaEventosParaHoy.getColumnModel().getColumn(0).setMinWidth(45);
        tablaEventosParaHoy.getColumnModel().getColumn(0).setWidth(45);
        tablaEventosParaHoy.getColumnModel().getColumn(3).setMaxWidth(45);
        tablaEventosParaHoy.getColumnModel().getColumn(3).setMinWidth(45);
        tablaEventosParaHoy.getColumnModel().getColumn(3).setWidth(45);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEventosParaHoy = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Eventos para hoy");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Eventos para hoy"));

        tablaEventosParaHoy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaEventosParaHoy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEventosParaHoyMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEventosParaHoy);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tablaEventosParaHoyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEventosParaHoyMouseClicked
        //descoponiendo & obteniendo identificador de agenda
        String identificador = hr.retornaValorTabla(0, tablaEventosParaHoy);
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
    }//GEN-LAST:event_tablaEventosParaHoyMouseClicked

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
            java.util.logging.Logger.getLogger(EventosParaHoy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EventosParaHoy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EventosParaHoy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EventosParaHoy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EventosParaHoy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEventosParaHoy;
    // End of variables declaration//GEN-END:variables
}
