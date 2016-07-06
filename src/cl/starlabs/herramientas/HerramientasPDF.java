
package cl.starlabs.herramientas;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Manuel Araya
 */
public class HerramientasPDF {

    FileOutputStream archivo    = null;
    Document documento          = null;
    
    public HerramientasPDF(String ruta, String tituloDocumento) {
        try {
            this.archivo    = new FileOutputStream(ruta);
            this.documento  = new Document(PageSize.LETTER);
            this.documento.addAuthor("StarLabs SyncPet");
            this.documento.addCreationDate();
            this.documento.addTitle(tituloDocumento);
            abrirArchivo();
            insertarLogo();
            documento.close();
        }catch(Exception e) {
            this.archivo    = null;
            this.documento  = null;
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar generar el PDF: "+e.getMessage());
        }
    }
    
    public boolean abrirArchivo() {
        try {
            this.documento.open();
            return documento.isOpen();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un problema abriendo el archivo: "+e.getMessage());
            return false;
        }
    }
    
    public void insertarLogo() {
        try {
            Image imagen = Image.getInstance("/cl/starlabs/imagenes/sistema/logo aplicacion slide.png");
        }catch(Exception e) {
            
        }
    }
    
}
