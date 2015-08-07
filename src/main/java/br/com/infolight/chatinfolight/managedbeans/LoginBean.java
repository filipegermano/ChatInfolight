package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private EmpresaDao empresaDao;    
    @Inject ChatBean chatBean;

    private Usuario usuario;
    private Usuario usuarioLogado;
    private Boolean logado;

    private String login;
    private String senha;
    private String cnpjEmpresa;

    public LoginBean() {
        setUsuario(new Usuario());        
    }        

    public String logar() {
        getUsuario().setLogin(getLogin());
        getUsuario().setSenha(getSenha());                

        if (getCnpjEmpresa() == null || getCnpjEmpresa().equals("")) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("cnpj", "1");
            getUsuario().setEmpresa(empresaDao.recuperaPorParametros(Empresa.RECUPERA_POR_CNPJ, parametros));
        } else {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("cnpj", getCnpjEmpresa());
            getUsuario().setEmpresa(empresaDao.recuperaPorParametros(Empresa.RECUPERA_POR_CNPJ, parametros));
        }

        usuarioLogado = usuarioDao.logar(getUsuario());
        
        
        if (usuarioLogado != null) {
            setUsuario(usuarioLogado);
            setLogado(true);
            return "/privado/home.xhtml?faces-redirect=true";
        } else {
            setLogado(false);
            return "/publico/login.xhtml?faces-redirect=true";
        }
    }

    public void sair() {
        try {
            setUsuarioLogado(null);
            setUsuario(new Usuario());
            setLogado(false);
            setLogin(null);
            setSenha(null);
            chatBean.salvarConversas();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ChatInfolight/");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Boolean getLogado() {
        return logado;
    }

    public void setLogado(Boolean logado) {
        this.logado = logado;
    }

    public String getLogin() {
        System.out.println(login + " - " + senha);
        return login;
    }

    public void setLogin(String login) {
        System.out.println(login + " - " + senha);
        this.login = login;
    }

    public String getSenha() {
        System.out.println(login + " - " + senha);
        return senha;
    }

    public void setSenha(String senha) {
        System.out.println(login + " - " + senha);
        this.senha = senha;
    }    

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }    

}
