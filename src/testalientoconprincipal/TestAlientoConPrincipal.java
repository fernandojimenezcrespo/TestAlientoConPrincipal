/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testalientoconprincipal;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sftp.SftpCanal;
import sftp.SftpCtes;
import utils.ComprobarDirectorios;
import utils.ControlFicheros;
import utils.CrearMensajeLog;
import utils.Utils;
/**
 *
 */
public class TestAlientoConPrincipal {
    
    public static String ftpDirectorioLocalPendiente=null; 
    public static String ftpDirectorioLocalProcesados=null;
    public static String ftpDirectorioLocalErrores=null;

    /*private static String ftpDirectorioLocalPendiente = null;
    private static String ftpDirectorioLocalProcesados = null;
    private static String ftpDirectorioLocalErrores = null;*/
    private static ControlFicheros controlFichero = new ControlFicheros();
    private static ComprobarDirectorios comprobarDirectorios = new ComprobarDirectorios();
    private static CrearMensajeLog creaLog=new CrearMensajeLog();

    
 

    public static void main(String[] args) {
         
        Utils utils = new Utils();
        creaLog.insertarLog("info","INICIO PROGRAMA");
        
        if (comprobarDirectorios.chequeaDirectorios()) {
            creaLog.insertarLog("info","COMPROBACION DE DIRECTORIOS OK");
        } else {
            creaLog.insertarLog("fatal"," :-(  ALGUN DIRECTORIO NO EXISTE ");
            utils.salirPrograma(":-(");
        }
                
 
        SftpCanal sftpCanal = new SftpCanal();
        ChannelSftp CanalTest = new ChannelSftp();
        HashMap<String, String> xMap = new HashMap();
        xMap = rellenaHas(xMap);
        
        
         try {

            CanalTest = sftpCanal.getChannel(xMap, 0);
            ListaFicherosMesActual(CanalTest);
            while (CanalTest != null) {
                System.out.println("Killing the session");
                creaLog.insertarLog("info","Desconectando Session SFTP");
                CanalTest.disconnect();
                creaLog.insertarLog("info", "Fin del programa.");
                System.exit(0);
            }

        } catch (JSchException ex) {
            creaLog.insertarLog("fatal","Error fatal "+ ex);
             System.out.print(ex);
        }
        
    }

    private static HashMap<String, String> rellenaHas(HashMap<String, String> xMap) {
        SftpCtes sftpCtes =new SftpCtes();
        xMap.put(sftpCtes.getSftp_HOST(), sftpCtes.getSftp_HOST());
        xMap.put(sftpCtes.getSftp_PORT(), sftpCtes.getSftp_PORT());
        xMap.put(sftpCtes.getSftp_USERNAME(), sftpCtes.getSftp_USERNAME());
        xMap.put(sftpCtes.getSftp_PASSWORD(), sftpCtes.getSftp_PASSWORD());
        xMap.put(sftpCtes.getSftp_LOC(), sftpCtes.getSftp_LOC());
        return xMap;

    }
        private static void ListaFicherosMesActual(ChannelSftp CanalTest) {
        Calendar hoy = Calendar.getInstance();
        int year = hoy.get(Calendar.YEAR);
        int month = hoy.get(Calendar.MONTH) + 1;
        String mes = "" + month;

        if (mes.length() < 2) {
            mes = "0" + mes;
        }
        try {
            CanalTest.cd("" + year + "/" + mes);
            CanalTest.lcd(ftpDirectorioLocalPendiente);
            String CarpetaActualRemota = CanalTest.pwd();
        
            String CarpetaActualLocal = CanalTest.lpwd();
            Vector filelist = CanalTest.ls(CarpetaActualRemota);
            for (int i = 0; i < filelist.size(); i++) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
                String fichero = entry.getFilename();
                String esDirectorio = entry.getLongname().substring(0, 1);
                OutputStream output = null;
                if (!esDirectorio.equals("d") && !esDirectorio.equals(".") && 
                        !controlFichero.ExisteFichero(ftpDirectorioLocalProcesados+"\\"+fichero) &&
                        !controlFichero.ExisteFichero(ftpDirectorioLocalPendiente+"\\"+fichero)) {
                    try {

                        output = new FileOutputStream(CarpetaActualLocal + "\\" + fichero);
                        creaLog.insertarLog( "info","DESCARGANDO EL FICHERO "+fichero);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TestAlientoConPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        CanalTest.get(fichero, output);
                } else {
                    creaLog.insertarLog("info","El " + fichero+ " ya lo he descargado ");
                }
            }

        } catch (SftpException ex) {
            Logger.getLogger(TestAlientoConPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
