/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.website.dao;

import br.com.zerto.website.entity.Usuario;
import java.util.List;

/**
 *
 * @author Zerato
 */
public class UsuarioDao extends GenericDao{
    
     public UsuarioDao(){
        super();
    }
    
    public void saveOrUpdate(Usuario obj) {
         super.saveOrUpdate(obj);
    }

    public void delete(Usuario obj) {
         super.delete(obj);
    }

    public Usuario find(Long Id) {
       return (Usuario) super.find(Usuario.class, Id);
    }

    public List<Usuario> findAll() {
        return (List<Usuario>) super.findAll(Usuario.class);
    }
}
