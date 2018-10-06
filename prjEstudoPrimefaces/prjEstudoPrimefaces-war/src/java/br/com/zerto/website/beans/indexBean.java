/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.website.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zerato
 */
@ManagedBean
@SessionScoped
public class indexBean {
    
    @ManagedProperty("#{loginBean}")
    private loginBean loginBean;
    private String login;
    private String senha;
    
    
    public void logout() throws Exception{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        
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

    public loginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(loginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    
}
