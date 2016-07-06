/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.herramientas;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.*;

/**
 *
 * @author Janno
 */
public class HerramientasFTP {
    
    public boolean subirArchivo(String ruta) throws SocketException, UnknownHostException, IOException
    {
        try
        {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName("172.20.0.2"));
            ftpClient.login("syncpet","alfacentauro$$");
            
            //Verificar conexión con el servidor.
            int reply = ftpClient.getReplyCode();
            System.out.println("Respuesta recibida de conexión FTP:" + reply);
            if(FTPReply.isPositiveCompletion(reply))
            {
                System.out.println("Conectado Satisfactoriamente al servidor");    
            }
            else
                {
                    System.out.println("Imposible conectarse al servidor");
                }
           
            //Verificar si se cambia de direcotirio de trabajo
            //ftpClient.changeWorkingDirectory("/");//Cambiar directorio de trabajo
            //System.out.println("Se cambió satisfactoriamente el directorio");
            
            
            //Activar que se envie cualquier tipo de archivo
            ftpClient.setFileType(FTP.IMAGE_FILE_TYPE);
            
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream(ruta));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile("/examenes", buffIn);//Ruta completa de alojamiento en el FTP
            
            buffIn.close(); //Cerrar envio de arcivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
            
            return true;
        }
        catch(Exception e)
                {
                    System.out.println(e.getMessage());
                    System.out.println("Ha ocurrido un inconveniente");
                    
                    return false;
                }
    }
}
