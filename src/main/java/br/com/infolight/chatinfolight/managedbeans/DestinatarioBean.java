package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Mensagem;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.MensagemDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@Named
@SessionScoped
public class DestinatarioBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private MensagemDao mensagemDao;

    private Usuario destinatario;
    private List<Mensagem> mensagens;
    

    public DestinatarioBean() {
        setDestinatario(new Usuario());
    }
    
    public Boolean exiteDestinatario() {
        if ((getDestinatario() == null) || (getDestinatario().getId() == null)) {
            return false;
        }
        return true;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }        

    public void setDestinatario(Usuario destinatario) {        
        this.destinatario = destinatario;
    }    

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
    
    

}
