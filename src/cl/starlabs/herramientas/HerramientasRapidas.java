/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.herramientas;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.Label;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Victor Manuel Araya
 */
public class HerramientasRapidas {

    public HerramientasRapidas() {
    }
    
    
     /**
     * Muestra un error en un JOptionPanne.
     * 
     * @param mensaje
     *            El error que monstrará al usuario
     * @return void
     */
    static public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje en un JOptionPanne.
     * 
     * @param mensaje
     *            El mensaje que monstrará al usuario
     * @return void
     */
    static public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "SyncPet", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Hace una pregunta en un JOptionPanne.
     * 
     * @param mensaje
     *            La pregunta que realizará al usuario
     * @return void
     */
    static public Integer preguntar(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "SyncPet", JOptionPane.YES_NO_OPTION);
    }
    
    /**
     * Verifica si un JTextField esta vacío
     * 
     * @param obj
     *            Es el JTextField o campo a verificar
     * @return boolean , Retorna verdadero si esta vacío acompañado de un mensaje de error y redireccionando al usuario al campo vacío, de lo contrario devuelve false
     */
    static public boolean esVacio(JTextField obj) {
        if(obj.getText().isEmpty()){
            mostrarError("Hay un campo vacio, por favor rellenelo");
            obj.requestFocus();
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Verifica si un JTextArea esta vacío
     * 
     * @param obj
     *            Es el JTextArea o campo a verificar
     * @return boolean , Retorna verdadero si esta vacío acompañado de un mensaje de error y redireccionando al usuario al campo vacío, de lo contrario devuelve false
     */
    static public boolean esVacio(JTextArea obj) {
        if(obj.getText().isEmpty()){
            mostrarError("Hay un campo vacio, por favor rellenelo");
            obj.requestFocus();
            return true;
        }else{
            return false;
        }
    }
    
    static public boolean esVacio(JDateChooser obj) {
        if(obj.getDate() == null ){
            mostrarError("Hay un campo vacio, por favor rellenelo");
            obj.requestFocus();
            return true;
        }else{
            return false;
        }
    }
    
    static public boolean esVacio(JComboBox obj) {
        if(obj.getItemCount() > 0) {
            if(obj.getSelectedItem().toString().isEmpty()){
                mostrarError("Hay un campo vacio, por favor rellenelo");
                obj.requestFocus();
                return true;
            }else{
                return false;
            }
        }else{
            mostrarError("Hay un JCombobox sin definir, por favor seleccione una opcion diferente");
            return true;
        }
    }
    
    /**
     * Devuelve el insertarTexto de un JTextField
     * 
     * @param obj
     *            Es el JTextField o campo a leer
     * @return String , contenido del campo enviado por parámetros
     */
    static public String contenido(JTextField obj) {
        return obj.getText();
    }
    
    /**
     * Devuelve el insertarTexto de un JTextArea
     * 
     * @param obj
     *            Es el JTextArea o campo a leer
     * @return String , contenido del campo enviado por parámetros
     */
    static public String contenido(JTextArea obj) {
        return obj.getText();
    }
    
    static public String contenido(JButton obj) {
        return obj.getText();
    }
    
    static public Date contenido(JDateChooser obj) {
        return obj.getDate();
    }
    
    /**
     * Devuelve el insertarTexto de un JTextArea convertido en Integer
     * 
     * @param obj
     *            Es el JTextArea o campo a leer
     * @return Integer , contenido del campo enviado por parámetros convertido en numeros
     * @exception en caso de error devolverá un mensaje de error y retorna 0
     */
    static public Integer contenidoInt(JTextField obj) {
        try {
            return Integer.parseInt(obj.getText());
        } catch (Exception e) {
            mostrarError("Las letras en un campo numérico no pueden ser convertidos en números");
            obj.selectAll();
            obj.requestFocus();
            return 0;
        }
    }
    
    /**
     * Define el largo máximo de carácter a soportar por un campo JTextField
     * 
     * @param obj
     *            Es el JTextField o campo a leer
     * @param largo
     *            Es el valor en numeros de tipo Integer que define la cantidad máxima de carácteres
     * @param evt
     *            Es el evento de KeyEvent que permite efectuar operaciones sobre el campo
     * @return void , una vez alcanzado el largo máximo el sistema consumirá los carácteres ingresados
     */
    static public void largoMaximo(JTextField obj ,Integer largo, java.awt.event.KeyEvent evt) {
        if(contenido(obj).length() > (largo-1)) {
            evt.consume();
        }
    }
    
    /**
     * Devuelve el insertarTexto de un JComboBox
     * 
     * @param obj
     *            Es el JComboBox o campo a leer
     * @return String , contenido del campo enviado por parámetros
     */
    static public String contenido(JComboBox obj) {
        return obj.getSelectedItem().toString();
    }
    
    /**
     * Devuelve el insertarTexto de un JComboBox
     * 
     * @param obj
     *            Es el JComboBox o campo a leer
     * @return Integer , contenido del campo enviado por parámetros convertido en numeros
     * @exception en caso de error devolverá un mensaje de error y retorna 0
     */
    static public Integer contenidoInt(JComboBox obj) {
        try {
            return Integer.parseInt(contenido(obj));
        } catch (Exception e) {
            mostrarError("No se pudo transformar el valor seleccionado a integer");
            obj.requestFocus();
            return 0;
        }
    }
    
     /**
     * Permite el ingreso de sólo numeros en un campo de cualquier tipo
     * 
     * @param evt
     *            Es un evento KeyEvent escuchado
     * @return void , si los valores capturados por KeyEvent no son números no serán recibidos por el campo
     */
    static public void ingresaSoloNumeros(java.awt.event.KeyEvent evt) {
        if(!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }
    
     /**
     * Permite el ingreso de sólo carácteres en un campo de cualquier tipo
     * 
     * @param evt
     *            Es un evento KeyEvent escuchado
     * @return void , si los valores capturados por KeyEvent son números no serán recibidos por el campo
     */
    static public void ingresaSoloCaracteres(java.awt.event.KeyEvent evt) {
        if(Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }
    
    /**
     * Permite el ingreso de sólo carácteres válidos en un rut chileno, o sea, números, puntos, guiones y letra K
     * 
     * @param evt
     *            Es un evento KeyEvent escuchado
     * @return void , si los valores capturados por KeyEvent son números no serán recibidos por el campo
     */
    static public void ingresaCaracteresRut(java.awt.event.KeyEvent evt) {
        if((!(Character.isDigit(evt.getKeyChar()))) && (!(evt.getKeyChar() == 'K')) && (!(evt.getKeyChar() == 'k')) && (!(evt.getKeyChar() == '-')) && (!(evt.getKeyChar() == '.'))) {
            evt.consume();
        } 
    }
    
    /**
     * Escribe un insertarTexto en el label indicado
     * 
     * @param obj
     *            Es un objeto JLabel donde se escribirá
     * @param texto
     *            Es la cadena de insertarTexto a insertar en dicho label
     * @return void , Setea el insertarTexto en dicho label
     */
    static public void insertarTexto(JLabel obj, String texto) {
        obj.setText(texto);
    }
    
    static public void insertarTexto(JButton obj, String texto) {
        obj.setText(texto);
    }
    
    static public void insertarTexto(JTextField obj, String texto) {
        obj.setText(texto);
    }

    static public void insertarTexto(JTextArea obj, String texto) {
        obj.setText(texto);
    }
    
    static public void insertarTexto(JComboBox obj, String texto) {
        obj.addItem(texto);
    }
    
    static public void activar(JLabel obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JTextArea obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JTextField obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JComboBox obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JButton obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JPanel obj) {
        obj.setEnabled(true);
    }
    
    static public void activar(JDateChooser obj) {
        obj.setEnabled(true);
    }
    
    static public void desactivar(JLabel obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JTextArea obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JTextField obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JComboBox obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JButton obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JPanel obj) {
        obj.setEnabled(false);
    }
    
    static public void desactivar(JDateChooser obj) {
        obj.setEnabled(false);
    }
    static public void focus(JLabel obj) {
        obj.requestFocus();
    }
    
    static public void focus(JTextArea obj) {
        obj.requestFocus();
    }
    
    static public void focus(JTextField obj) {
        obj.requestFocus();
    }
    
    static public void focus(JComboBox obj) {
        obj.requestFocus();
    }
    
    static public void focus(JButton obj) {
        obj.requestFocus();
    }
    
    static boolean compararIgnoreCase(JTextField obj, String cadena) {
        if (contenido(obj).compareToIgnoreCase(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    static boolean compararIgnoreCase(JTextArea obj, String cadena) {
        if (contenido(obj).compareToIgnoreCase(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
        
    static boolean compararIgnoreCase(JComboBox obj, String cadena) {
        if (contenido(obj).compareToIgnoreCase(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    static boolean comparar(JTextField obj, String cadena) {
        if (contenido(obj).compareTo(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    static boolean comparar(JTextArea obj, String cadena) {
        if (contenido(obj).compareTo(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
        
    static boolean comparar(JComboBox obj, String cadena) {
        if (contenido(obj).compareTo(cadena) == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    public static int fechasdiferenciaendias(Date fechainicial, Date fechafinal) {

        SimpleDateFormat df = new SimpleDateFormat();
        String fechainiciostring = df.format(fechainicial);
        try {
            fechainicial = df.parse(fechainiciostring);
        }catch (ParseException ex) {
        }

        String fechafinalstring = df.format(fechafinal);
        try {
            fechafinal = df.parse(fechafinalstring);
        }catch(ParseException ex) {
        }

        long fechainicialms = fechainicial.getTime();
        long fechafinalms = fechafinal.getTime();
        long diferencia = fechafinalms - fechainicialms;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ( (int) dias);
    }
    
    public String retornaValorTabla(Integer posicion, JTable tabla) {
        if(tabla.getSelectedColumn() >= 0) {
            DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
            return String.valueOf(modelo.getValueAt(tabla.getSelectedRow(), posicion));
        }else{
            return "";
        }
    }
    
    public Date recuperarFecha(JCalendar obj) {
        try {
            return obj.getDate();
        } catch (Exception e) {
            return null;
        }
    }
    
    public String formatearFecha(Date fecha) {
        try {
            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
            return dt.format(fecha);
        } catch (Exception e) {
            return "Error: No se pudo transformar la fecha";
        }
    }
    
    public String formatearHoraDesdeFecha(Date fecha) {
        try {
            SimpleDateFormat dt = new SimpleDateFormat("hh:mm");
            return dt.format(fecha);
        } catch (Exception e) {
            return "Error: No se pudo transformar la fecha";
        }
    }
    
    public Date fechaHoraInicial(Date fecha) {
        Date fechaAux = fecha;
        try {
            fechaAux.setHours(00);
            fechaAux.setMinutes(00);
            fechaAux.setSeconds(00);
            return fechaAux;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Date fechaHoraFinal(Date fecha) {
        Date fechaAux = fecha;
        try {
            fechaAux.setHours(23);
            fechaAux.setMinutes(59);
            fechaAux.setSeconds(59);
            return fechaAux;
        } catch (Exception e) {
            return null;
        }
    }

}
