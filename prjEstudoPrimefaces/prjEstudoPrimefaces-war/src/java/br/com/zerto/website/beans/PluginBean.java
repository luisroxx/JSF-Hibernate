/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.website.beans;

import br.com.zerto.pluginmanager.Plugin;
import br.com.zerto.pluginmanager.PluginManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;



/**
 *
 * @author Zerato
 */

@ManagedBean
public class PluginBean {
    
    private PluginManager pm;
    private UploadedFile uploadedFile;

    @PostConstruct
    public void init(){
        try{
            pm = PluginManager.getInstance();
        }catch (Exception e){
            
        }
    }
    
    public void install() throws IOException{
      String pluginsHome="C:\\Users\\Zerato\\Documents\\NetBeansProjects\\PluginManager\\plugins\\";
      String fileName = FilenameUtils.getName(uploadedFile.getFileName());

      Path folder = Paths.get(pluginsHome);
      String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

      Path fullPath = Paths.get(pluginsHome + "/" + fileName);
      String result = "null";

      try (InputStream input = uploadedFile.getInputstream()) {
          Files.copy(input, fullPath, StandardCopyOption.REPLACE_EXISTING);
      }

      try {
              result = pm.installPlugin(fullPath);
      } catch (Exception e) {
      }

      FacesContext.getCurrentInstance().addMessage(
              null,
              new FacesMessage(String.format(
                      "'%s'!", result)));
    }

    public boolean isActive(Plugin plugin) {
        return plugin.isEnable();
    }
    
    public void disable(Plugin plugin) {
        String result = "null";
        try {
            result = pm.disablePlugin(plugin.getNome());
        } catch (Exception e) {
        }
        FacesContext.getCurrentInstance().addMessage(
             null,
             new FacesMessage(String.format(
                     "'%s'!", result)));
    }
    
    public void enable(Plugin plugin) {
        String result = "null";
        try {
            result = pm.enablePlugin(plugin.getNome());
        } catch (Exception e) {
        }
        FacesContext.getCurrentInstance().addMessage(
             null,
             new FacesMessage(String.format(
                     "'%s'!", result)));
    }
    
    public void uninstall(Plugin plugin) {
        String result = "null";
        try {
            result = pm.uninstallPlugin(plugin.getNome());
        } catch (Exception e) {
        }
        FacesContext.getCurrentInstance().addMessage(
              null,
              new FacesMessage(String.format(
                      "'%s'!", result)));
    }
    
    public PluginManager getPm() {
        return pm;
    }

    public void setPm(PluginManager pm) {
        this.pm = pm;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
