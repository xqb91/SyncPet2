/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.inicia;
import cl.starlabs.vista.login.IniciarSesion;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;

public class ArrancarSyncPet extends JWindow {
    
    public void iniciar() throws InterruptedException {
        Splash ini = new Splash();
        this.add(ini, BorderLayout.CENTER);
        this.setSize(ini.getWidth(), ini.getHeight());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        ini.comprobacionInicial();
        this.dispose();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            new ArrancarSyncPet().iniciar();
        } catch (Exception e) {
            
        }
    }
    
}
