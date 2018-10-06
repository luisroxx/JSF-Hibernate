/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.website.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import br.com.zerto.website.dao.UsuarioDao;
import br.com.zerto.website.entity.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Zerato
 */
@ManagedBean
@SessionScoped
public class loginBean{
    
    private String login;
    private String senha;
    private String message;
    
    public void logar() throws Exception{
        UsuarioDao userDao = new UsuarioDao();
        Usuario x = new Usuario();
        x.setUsuarioLogin(login);
        x.setUsuarioSenha(senha);
        x.setUsuarioNome(login);
        //userDao.saveOrUpdate(x);
        
        if(senha.equals("admin")  | login.equals("admin")){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpSession session = request.getSession(true);
            session.setAttribute("idUsuarioLogado", 10);
            message = "Logado";
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }else
        {
            message = "Não Logado";
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
