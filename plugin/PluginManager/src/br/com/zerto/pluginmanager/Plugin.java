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
public class Plugin {
    
    Path path;
    String nome;
    boolean enable;
    PluginDescriptor descriptor;

    public Plugin(Path path, String nome, boolean isEnable) {
        
        this.path = path;
        this.nome = nome;
        this.enable = isEnable;
        descriptor = new PluginDescriptor("Path do plugin", "Nome do plugin", "Habilitado");
    }
    
    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public PluginDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(PluginDescriptor descriptor) {
        this.descriptor = descriptor;
    }
}
