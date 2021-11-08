/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;
import java.util.Date;
import sftp.SftpCtes;
import testalientoconprincipal.TestAlientoConPrincipal;


public class ComprobarDirectorios {

    public boolean chequeaDirectorios() {
        CrearMensajeLog log = new CrearMensajeLog();

        SftpCtes sftpCtes = new SftpCtes();
        ControlVariable controlVariable = new ControlVariable();
        ControlFicheros controlFichero = new ControlFicheros();
        testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalPendiente = (sftpCtes.getSftp_DIRECTORIO_LOCAL_PENDIENTE());
        if (!controlVariable.ControlVariable(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalPendiente)) {
            log.insertarLog("fatal", "No existe la variable ftpDirectorioLocalPendiente");
            return false;
        }
        if (!controlFichero.ExisteDirectorio(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalPendiente)) {
            log.insertarLog("fatal", "No existe el Directorio " + testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalPendiente);
            return false;
        }
        testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados = (sftpCtes.getSftp_DIRECTORIO_LOCAL_PROCESADOS());
        if (!controlVariable.ControlVariable(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados)) {
            log.insertarLog("fatal", "No existe la variable ftpDirectorioLocalProcesados");
            return false;
        }
        if (!controlFichero.ExisteDirectorio(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados)) {
            log.insertarLog("fatal", "No existe el Directorio " + testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados);
            return false;
        }
        if (!compruebaDirectoriosAAAAMM())
        {
            log.insertarLog("fatal","No he podido crear el directorio AAAAMM");
            return false;
        }
        testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalErrores = (sftpCtes.getSftp_DIRECTORIO_LOCAL_ERRORES());
        if (!controlVariable.ControlVariable(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalErrores)) {
            log.insertarLog("fatal", "No existe la variable ftpDirectorioLocalErrores");
            return false;
        }
        if (!controlFichero.ExisteDirectorio(testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalErrores)) {
            log.insertarLog("fatal", "No existe el Directorio " + testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalErrores);
            return false;
        }
        return true;
    }

    public boolean compruebaDirectoriosAAAAMM() {
        Calendar fecha = Calendar.getInstance();
        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        String directorio = ""+testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados +"\\"+ year + mes;
        TestAlientoConPrincipal.ftpDirectorioLocalProcesados = ""+testalientoconprincipal.TestAlientoConPrincipal.ftpDirectorioLocalProcesados +"\\"+ year + mes;;
        ControlFicheros controlFicheros = new ControlFicheros();
        if (controlFicheros.ExisteDirectorio(directorio)) {
            return true;
        } else {
            if (controlFicheros.CrearDirectorios(directorio)) {
                return true;
            } else {
                return false;
            }
        }

    }
}
