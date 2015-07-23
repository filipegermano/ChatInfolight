package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
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

    private Usuario usuario;    
    private Usuario usuarioLogado;
    private Boolean logado;        
    
    public LoginBean() {
        setUsuario(new Usuario());
    }

    public String logar() {        
        usuarioLogado = usuarioDao.logar(getUsuario());
        
        if (usuarioLogado != null) {
            setUsuario(usuarioLogado);            
            setLogado(true);
            return "/privado/home.xhtml?faces-redirect=true";
        }
        else {
            setLogado(false);
            return "/publico/login.xhtml?faces-redirect=true";
        }
    }
    
    public String sair(){
        setUsuarioLogado(null);
        setUsuario(new Usuario());
        setLogado(false);
        return "/publico/login.xhtml?faces-redirect=true";
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

}
