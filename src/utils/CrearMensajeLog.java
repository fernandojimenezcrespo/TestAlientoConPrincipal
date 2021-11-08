package utils;

import org.apache.log4j.Logger;
  
public class CrearMensajeLog {
public static Logger log = Logger.getLogger("TestAlientoSFTP"); 
    public void insertarLog(String tipo,String mensaje) {
 
        grabaLog(tipo, mensaje);
    }
    public void insertarLog( ) {
        grabaLog("error","sin mensaje");
    }
private void grabaLog(String tipo,String mensaje)
{
    switch (tipo)
       {
        case "info":
            log.info(mensaje);
            break;
        case "warn":
            log.warn(mensaje);
            break;
        case "error":
            log.error(mensaje);
            break;
        case "fatal":
            log.fatal(mensaje);
            break;
        default:
            log.error("Has pasado parametro incorrecto en el log y el mensaje es:"+mensaje);
             
        }
}
    
    
    
}