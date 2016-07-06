/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.herramientas;

/**
 *
 * @author Victor Manuel Araya
 */
public class HerramientasTelefono {
    
    /**
     * formatea un número telefónico fijo chileno desde un String.
     * 
     * @param telefono
     *            Numero telefónico que será formateado
     * @return String con numero telefónico en formato +56XXXXXXXX
     */
    static public String formatearTelefono(String telefono) {
        if(telefono.substring(0,4).compareToIgnoreCase("+562") != 0)
        {
            return "+562"+telefono;
        }else{
            return telefono;
        }
    }
    
    /**
     * formatea un número celular fijo chileno desde un String.
     * 
     * @param celular
     *            Numero celular que será formateado
     * @return String con numero celular en formato +569XXXXXXXX
     */
    static public String formatearCelular(String celular) {
        if(celular.substring(0,4).compareToIgnoreCase("+569") != 0)
        {
            return "+569"+celular;
        }else{
            return celular;
        }
    }
        
    /**
     * Valida un número telefónico fijo chileno desde un String.
     * 
     * @param telefono
     *            Numero telefónico que será verificado
     * @return true si el número es valido, de lo contrario false
     */
    static public boolean validarTelefono(String telefono) {
        if(telefono.length() == 7) {
            return true;
        }else{
            if(telefono.length() == 11) {
                return telefono.substring(0,4).compareToIgnoreCase("+562") == 0;
            }else{
                return false;
            }
        }
    }
    
    
    /**
     * Valida un número celular fijo chileno desde un String.
     * 
     * @param celular
     *            Numero celular que será verificado
     * @return true si el celular es valido, de lo contrario false
     */
    static public boolean validarCelular(String celular) {
        if(celular.length() == 8) {
            return true;
        }else{
            if(celular.length() == 12) {
                return celular.substring(0,4).compareToIgnoreCase("+569") == 0;
            }else{
                return false;
            }
        }
    }
}
