package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@RequestScoped
@Named
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    
    private Usuario usuario;
    
    public UsuarioBean() {
        setUsuario(new Usuario());
    }        
        
    public void logar() {
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
