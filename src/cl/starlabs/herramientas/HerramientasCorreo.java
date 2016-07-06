package cl.starlabs.herramientas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Victor Manuel Araya
 */
public class HerramientasCorreo {
    private static final String caracteres = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    /**
     * Valida una dirección de correo electrónico desde un String.
     * 
     * @param email
     *            Correo que será validado
     * @return true si el correo es valido, de lo contrario false
     */
    public static boolean validarEmail(String email) {
 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(caracteres);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
}
