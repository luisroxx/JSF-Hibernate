/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.pluginmanager;

import java.nio.file.Path;

/**
 *
 * @author Zerato
 */
public class PluginDescriptor {
    
    String path;
    String nome;
    String enable;

    public PluginDescriptor(String path, String nome, String enable) {
        this.path = path;
        this.nome = nome;
        this.enable = enable;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
    
    
    
}
