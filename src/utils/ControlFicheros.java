/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;

 
public class ControlFicheros {
 CrearMensajeLog crearMensaje= new CrearMensajeLog(); 
 Utils utils=new Utils();
    
public boolean ExisteDirectorio(String directorio)
{
        File directorioFichero = new File(directorio);
        if (!directorioFichero.exists()) {
          return false;
        }
        if (!directorioFichero.isDirectory())
        {
            return false;
        }
        return true;
    }
public boolean ExisteFichero(String fichero)
{
        File directorioFichero = new File(fichero);
        if (!directorioFichero.exists()) {
          return false;
        }
        if (!directorioFichero.isFile())
        {
            return false;
        }
        return true;
    }

public boolean CrearDirectorios (String carpeta){
         File directorios = new File(carpeta);
        if (!directorios.exists()) {
            if (directorios.mkdirs()) {
                crearMensaje.insertarLog("info", "He creado el directorio"+carpeta);
                return true;
            } else {
                crearMensaje.insertarLog("fatal", "No he podido crear el directorio"+carpeta);
                utils.salirPrograma("Error. No he podido crear el directorio "+carpeta);
                return false;
                 
            }
        }
        return false;
    }
}


