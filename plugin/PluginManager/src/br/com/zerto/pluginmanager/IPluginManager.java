/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.pluginmanager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zerato
 */
public interface IPluginManager {
      
    List<Plugin> plugins = new ArrayList<>();
    
    static PluginManager pluginManager = new PluginManager();
    
    PluginManager getInstance();
    
    void adicionarPlugin();
    
    void removerPlugin();
    
    void enablePlugin();
    
    void disablePlugin();
    
    void listarPlugins();
    
    Plugin getPlugin();
    
    void init();
}
