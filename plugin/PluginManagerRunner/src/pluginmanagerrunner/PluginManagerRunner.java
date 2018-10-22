/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pluginmanagerrunner;

import br.com.zerto.pluginmanager.PluginManager;

/**
 *
 * @author Zerato
 */
public class PluginManagerRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        PluginManager x = PluginManager.getInstance();
        String path = System.getProperty("user.dir") + "\\plugins\\";
        path += "123-war.zip";
        System.out.println(path);
        String pluginNome = "123-war.war";
        String result;
        //result = x.uninstallPlugin(pluginNome);
        //System.out.println(result);
//        result = x.installPlugin(new File(path).toPath());
//        System.err.println(result);
//        result = x.enablePlugin(pluginNome);
//        System.err.println(result);
//        result = x.disablePlugin(pluginNome);
//        System.err.println(result);
//        result = x.enablePlugin(pluginNome);
//        System.err.println(result);
//        result = x.uninstallPlugin(pluginNome);
//        System.err.println(result);
        
    }
    
}
