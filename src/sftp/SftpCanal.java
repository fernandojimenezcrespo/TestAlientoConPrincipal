/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sftp;
  
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.Session;
import java.util.Map;
import java.util.Properties;
import utils.ControlFicheros;
import utils.ControlVariable;
import utils.CrearMensajeLog;
import utils.Desencriptar;
import utils.Utils;

         
 

public class SftpCanal {

    Session session = null;
    Channel channel = null;
    SftpCtes sftpCtes=new SftpCtes();
    ControlVariable controlVariable=new ControlVariable();
    ControlFicheros controlFichero =new ControlFicheros();
    Utils utils =new Utils();


    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {
        CrearMensajeLog creaLog=new CrearMensajeLog();
        String ftpHost = sftpDetails.get(sftpCtes.getSftp_HOST());
        if (!controlVariable.ControlVariable(ftpHost))
        {   creaLog.insertarLog("error","Error al configurar ftpHost");
            utils.salirPrograma("No existe la variable ftpHost");}
        
        String port = sftpDetails.get(sftpCtes.getSftp_PORT());
        if (!controlVariable.ControlVariable(port))
            {   creaLog.insertarLog("error","Error al configurar port");
            utils.salirPrograma("No existe la variable port");}
        String ftpUserName = sftpDetails.get(sftpCtes.getSftp_USERNAME());
        if (!controlVariable.ControlVariable(ftpUserName))
            {   creaLog.insertarLog("error","Error al configurar ftpUserName");
            utils.salirPrograma("No existe la variable ftpUserName");}
        String ftpPassword = sftpDetails.get(sftpCtes.getSftp_PASSWORD());
        if (!controlVariable.ControlVariable(ftpPassword))
            {   creaLog.insertarLog("error","Error al configurar ftpPassword");
            utils.salirPrograma("No existe la variable ftpPassword");}
        Desencriptar desencriptar=new Desencriptar();
        ftpUserName= desencriptar.descifraClave(ftpUserName);
        ftpPassword= desencriptar.descifraClave(ftpPassword);
        
        int ftpPort = Integer.parseInt(port);
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        JSch jsch = new JSch(); // Crear objeto JSch
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // Obtenga un objeto Session de acuerdo con el nombre de usuario, la IP del host y el puerto
         System.out.println("Session created.");
           creaLog.insertarLog("info","Session created");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // establecer contraseña
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        SftpProxy sftpProxy=new SftpProxy();
        ProxySOCKS5 proxy=sftpProxy.setProxy();
        /*----------
         ProxySOCKS5    proxy = new ProxySOCKS5( "proxy.sacyl.es",1080 );
            ( (ProxySOCKS5) proxy ).setUserPasswd( "06384936K","12345678" );
 
         --------------------*/
        session.setConfig(config); // Establecer propiedades para el objeto Session
        session.setTimeout(timeout); // establecer el tiempo de espera
        if (proxy==null)
            //System.out.println("NO EXISTE PROXY");
           creaLog.insertarLog("info","trabajando sin PROXY");
        else
            {   creaLog.insertarLog("info","trabajando con PROXY");
            session.setProxy(proxy);}
        
        session.connect(); // Establecer un enlace a través de Session
        
        System.out.println("Session connected.");
        creaLog.insertarLog("info","Session connected");

        System.out.println("Opening Channel.");
        channel = session.openChannel("sftp"); // Abre el canal SFTP
        channel.connect(); // Establecer una conexión al canal SFTP
        
        creaLog.insertarLog("info","Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName + ", returning: " + channel);
        System.out.println("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
         
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}

