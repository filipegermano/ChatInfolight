package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Mensagem;
import br.com.infolight.chatinfolight.entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@ApplicationScoped
@Named
public class ConversasBean implements Serializable {

    private List<Mensagem> conversa;
    
    @PostConstruct
    public void init(){
        setConversa(new ArrayList<Mensagem>());
    }
    
    public List<Mensagem> getConversa() {
        return conversa;
    }

    public void setConversa(List<Mensagem> conversa) {
        this.conversa = conversa;
    }

}
