package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Mensagem;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.MensagemDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@Named
@RequestScoped
public class ChatBean implements Serializable {
    
    @Inject
    private MensagemDao mensagemDao;
    @Inject
    private UsuarioDao usuarioDao;
    @Inject    
    private DestinatarioBean destinatarioBean;            
    private Mensagem mensagem;
    private Boolean atualizaScroll;    
    private List<Mensagem> mensagens;
    private List<Usuario> usuarios;
            
    public ChatBean() {
        setMensagem(new Mensagem());        
    }
    
    @PostConstruct
    public void init(){
//        setMensagens(mensagemDao.listar(Mensagem.LISTA_TODAS_MENSAGENS));
        setUsuarios(usuarioDao.listar(Usuario.LISTA_TODOS_CLIENTES));
    }
    
    public void salvar(Usuario usuarioLogado, Usuario destinatario){
        getMensagem().setRemetente(usuarioLogado);
        getMensagem().setDestinatario(destinatario);
        mensagemDao.salvar(getMensagem());
    }
    
    public void preparaConversa(Usuario remetente, Usuario destinatario){
        
        getDestinatarioBean().setDestinatario(destinatario);
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("remetente", remetente);
        parametros.put("destinatario", destinatario);
        
        Integer tamanhoAnterior = getMensagens() != null ? getMensagens().size() : 0;
        setMensagens(mensagemDao.listar(Mensagem.LISTA_POR_DESTINATARIO, parametros));
        Collections.sort(getMensagens());                
        Integer tamanhoAtual = getMensagens() != null ? getMensagens().size() : 0;
        
        verificaSizeMensagens(tamanhoAnterior, tamanhoAtual);
    }
    
    private void verificaSizeMensagens(Integer tamanhoAnterior, Integer tamanhoAtual){
        setAtualizaScroll(tamanhoAnterior < tamanhoAtual);
    }
    
    public Boolean verificaRemetente(Usuario usuarioLogado, Usuario remetente){
        System.out.println(usuarioLogado.getLogin() + " - " + remetente.getLogin());
        return usuarioLogado.equals(remetente);        
    }
    
    public Mensagem getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }   

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }            

    public DestinatarioBean getDestinatarioBean() {
        return destinatarioBean;
    }

    public Boolean getAtualizaScroll() {
        return atualizaScroll;
    }

    public void setAtualizaScroll(Boolean atualizaScroll) {
        this.atualizaScroll = atualizaScroll;
    }        
        
}