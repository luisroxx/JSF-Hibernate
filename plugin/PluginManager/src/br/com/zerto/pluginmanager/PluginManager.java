/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.pluginmanager;

import br.com.zerto.deploy.GlassFishCommandExecuter;
import br.com.zerto.utils.FileUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zerato
 */
public class PluginManager{

    public static PluginManager pluginManager;
    private static GlassFishCommandExecuter glassFishExecuter = new GlassFishCommandExecuter();
    private String pluginFolderPath = "C:\\Users\\Zerato\\Documents\\NetBeansProjects\\PluginManager\\plugins";
    private List<Plugin> plugins = new ArrayList<>();
    
    public synchronized static PluginManager getInstance() throws Exception {
        if (pluginManager == null)
            pluginManager = PluginManager.init();
        return pluginManager;
    }
    
    private static PluginManager init(){
        PluginManager pm = new PluginManager();
        try {
            pm.load();
            return pm;
        } catch (Exception e) {
        }
        return pm;
    }
    
    private void load() throws Exception{
        //Carregar os valores da pasta se tiver
        List<Path> warPaths = FileUtils.listWarForFolderPath(new File(pluginFolderPath).toPath());
        System.err.println("tamanho" + warPaths.size());
        for(Path p : warPaths){
            System.err.println(installPlugin(p));
        }
    }
    
    public String installPlugin(Path pluginPath) throws Exception{
        pluginPath = FileUtils.expandIfZip(pluginPath);
        System.err.println("path " + pluginPath);
        if (!pluginPath.toString().endsWith(".war")){
            pluginPath = FileUtils.findWithEnding(pluginPath, ".war");
        }
        
        if (pluginPath == null){
            return "Erro de Path";
        }
        
        String pluginName = pluginPath.toString();
        pluginName = pluginName.substring(pluginName.lastIndexOf("\\")+1);
        
        //Verifica se ja existe
        if(getPlugin(pluginName) != null){
            return "Plugin já registrado";
        }
        
        System.err.println("PluginPath = " + pluginPath);
        System.err.println("PluginName = " + pluginName);
        
        //Deploy
        String result = glassFishExecuter.deploy(pluginPath.toString());
        
        if(result.contains("successfully")){
            plugins.add(new Plugin(pluginPath, pluginName, true)); 
        }
        
        return result;
    }
    
    public String uninstallPlugin(String pluginName) throws Exception{
        Plugin plugin = getPlugin(pluginName);
        if (plugin != null){
            //Undeploy
            String result = glassFishExecuter.undeploy(plugin.nome);
            if(result.contains("successfully")){
                //Deletar Pasta
                FileUtils.delete(new File(pluginFolderPath).toPath(), pluginName);

                //Deletar da classe;
                plugins.remove(plugin);
            }
            return result;
        } else{
            return "Plugin inválido";
        }
    }
    
    public String enablePlugin(String pluginName) throws Exception{
        Plugin plugin = getPlugin(pluginName);
        if (plugin != null){
            if (!plugin.isEnable()){
                //enable
                String result = glassFishExecuter.enable(plugin.nome);

                //Alterar estado plugin;
                plugin.setEnable(true);
                if(result.contains("successfully")){
                    plugins.set(getPluginIndex(plugin.nome), plugin);
                }
                return result;
            } else{
                return "Plugin já está habilitado";
            }
        } else{
            return "Plugin inválido";
        }
    }
    
    public String disablePlugin(String pluginName) throws Exception{
        Plugin plugin = getPlugin(pluginName);
        if (plugin != null){
            if (plugin.isEnable()){
                //Enable
                String result = glassFishExecuter.disable(plugin.nome);

                //Alterar estado plugin;
                plugin.setEnable(false);
                if(result.contains("successfully")){
                    plugins.set(getPluginIndex(plugin.nome), plugin);
                }
                return result;
            } else{
                return "Plugin já está habilitado";
            }
        } else{
            return "Plugin inválido";
        }
    }
    
    public List<Plugin> listPlugins(){
        return plugins;
    }
    
    private Plugin getPlugin(String nome){
        for(Plugin p : plugins){
            System.err.println("nome p " + p.getNome());
            if (p.getNome().toLowerCase().equals(nome.toLowerCase())){
                return p;
            }
        }
        return null;
    }
    
    private int getPluginIndex(String nome){
        int i = 0;
        for(Plugin p : plugins){
            if (p.getNome().toLowerCase().equals(nome.toLowerCase())){
                return i;
            }
            i++;
        }
        return -1;
    }
}
