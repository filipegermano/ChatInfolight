package br.com.infolight.chatinfolight.managedbeans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@RequestScoped
@Named
public class NavegacaoBean implements Serializable {    
    
    public String usuarioForm(){
        return "/privado/usuario/form.xhtml";
    }
    
    public String usuarioLista(){
        return "/privado/usuario/lista.xhtml";
    }
    
}
