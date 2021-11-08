/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sftp;

import com.jcraft.jsch.ProxySOCKS5;
import utils.Desencriptar;
import utils.LeerXML;

 

/**
 *
 */
public class SftpProxy {

    private String proxyExiste = null;
    private String proxyHost = null;
    private int proxyPort = 0;
    private String proxyUser = null;
    private String proxyPass = null;

    public ProxySOCKS5 setProxy() {
        asignaValores();
        if (proxyExiste.equals("1")) {
            ProxySOCKS5 proxy = new ProxySOCKS5(proxyHost, proxyPort);
            proxy.setUserPasswd(proxyUser, proxyPass);
            return proxy;
        } else {
            return null;
        }
    }

    private void asignaValores() {
        LeerXML leerXML = new LeerXML();
        Desencriptar desencriptar = new Desencriptar();
        proxyExiste = leerXML.devuelveValor("proxyExiste");
        proxyHost = leerXML.devuelveValor("proxyHost");
        proxyPort = Integer.parseInt(desencriptar.descifraClave(leerXML.devuelveValor("proxyPort")));
        proxyUser = desencriptar.descifraClave(leerXML.devuelveValor("proxyUser"));
        proxyPass = desencriptar.descifraClave(leerXML.devuelveValor("proxyPass"));

    }
}
