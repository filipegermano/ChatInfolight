package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Mensagem;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.StatusMensagem;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import br.com.infolight.chatinfolight.persistencia.MensagemDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.ArrayList;
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

//    @Inject
//    private MensagemDao mensagemDao;
//    @Inject
//    private UsuarioDao usuarioDao;
    @Inject
    private DestinatarioBean destinatarioBean;
    @Inject
    private ConversasBean conversasBean;
    @Inject
    private ClientesBean clientesBean;
    private Mensagem mensagem;
    private Boolean atualizaScroll;
    private List<Mensagem> mensagens;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosParaCliente;
    private Integer mensagensPendentes;

    public ChatBean() {
        setMensagem(new Mensagem());
    }

    @PostConstruct
    public void init() {
//        setMensagens(mensagemDao.listar(Mensagem.LISTA_TODAS_MENSAGENS));
        //setUsuariosParaCliente(usuarioDao.listar(Usuario.LISTA_PARA_CLIENTE));
        
        //setUsuarios(usuarioDao.listar(Usuario.LISTA_TODOS_CLIENTES));
        setUsuarios(clientesBean.getUsuarios());
    }

    public void salvar(Usuario usuarioLogado, Usuario destinatario) {
        getMensagem().setRemetente(usuarioLogado);

        if (usuarioLogado.getTipoUsuario().name().equals(TipoUsuario.Consultor.name())) {
            getMensagem().setDestinatario(destinatario);
        } else {
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            parametros.put("id", 1l);
//            Usuario consultorPadrao = usuarioDao.recuperaPorParametros(Usuario.RECUPERA_POR_ID, parametros);
            Usuario consultorPadrao = clientesBean.getUsuarioPadrao();
            getMensagem().setDestinatario(consultorPadrao);
        }
        if ((getMensagem().getDestinatario() != null) && (getMensagem().getRemetente() != null) && (!getMensagem().getMensagem().equals(""))) {
//            mensagemDao.salvar(getMensagem());

            getConversasBean().getConversa().add(getMensagem());

            setMensagem(new Mensagem());
        }
    }

    public void preparaConversa(Usuario remetente, Usuario destinatario) {
        Map<String, Object> parametros = new HashMap<String, Object>();

//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("id", 1l);
//        Usuario consultorPadrao = usuarioDao.recuperaPorParametros(Usuario.RECUPERA_POR_ID, param);
        Usuario consultorPadrao = clientesBean.getUsuarioPadrao();

        if (remetente.getTipoUsuario().name().equals(TipoUsuario.Consultor.name())) {
            getDestinatarioBean().setDestinatario(destinatario);
            parametros.put("destinatario", destinatario);

            Map<String, Object> msgDest = new HashMap<String, Object>();
            msgDest.put("remetente", destinatario);
//            List<Mensagem> mensagensPendentes = mensagemDao.listar(Mensagem.LISTA_ENVIADAS_POR_REMETENTE, msgDest);
//teste
            List<Mensagem> mensagensPendentes = listaEnviadoPorRemetente(getConversasBean().getConversa(), destinatario);

            for (Mensagem msg : mensagensPendentes) {
                msg.setStatusMensagem(StatusMensagem.Entregue);
//                mensagemDao.alterar(msg);
            }

            if ((destinatario.getId() != null) && (remetente.getId() != null)) {
//                setMensagens(mensagemDao.listar(Mensagem.LISTA_POR_LOGADO_REMETENTE_DESTINATARIO, parametros));
//teste                
                setMensagens(listaPorTipoRemetenteDestinatario(getConversasBean().getConversa(), destinatario)    );
                Collections.sort(getMensagens());
            }
        } else {
            getDestinatarioBean().setDestinatario(consultorPadrao);
            parametros.put("destinatario", remetente);

            Map<String, Object> msgDest = new HashMap<String, Object>();
            msgDest.put("remetente", consultorPadrao);
//            List<Mensagem> mensagensPendentes = mensagemDao.listar(Mensagem.LISTA_ENVIADAS_POR_REMETENTE, msgDest);
//teste
            List<Mensagem> mensagensPendentes = listaEnviadoPorRemetente(getConversasBean().getConversa(), consultorPadrao);

            for (Mensagem msg : mensagensPendentes) {
                msg.setStatusMensagem(StatusMensagem.Entregue);
//                mensagemDao.alterar(msg);
            }

//            setMensagens(mensagemDao.listar(Mensagem.LISTA_PARA_CLIENTE, parametros));
//teste            
            setMensagens(listaParaCliente(getConversasBean().getConversa(), remetente));
            Collections.sort(getMensagens());
        }

    }

    private void verificaSizeMensagens(Integer tamanhoAnterior, Integer tamanhoAtual) {
        setAtualizaScroll(tamanhoAnterior < tamanhoAtual);
    }

    public Boolean verificaRemetente(Usuario usuarioLogado, Usuario remetente) {
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

    public List<Usuario> getUsuariosParaCliente() {
        return usuariosParaCliente;
    }

    public void setUsuariosParaCliente(List<Usuario> usuariosParaCliente) {
        this.usuariosParaCliente = usuariosParaCliente;
    }

    public void verificaPendentes(Usuario remetente) {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("remetente", remetente);
//        List<Mensagem> ms = mensagemDao.listar(Mensagem.LISTA_ENVIADAS_POR_REMETENTE, parametros);
//teste
        
        List<Mensagem> ms = listaEnviadoPorRemetente(getConversasBean().getConversa(), remetente);
        System.out.println("Enviados = " + ms.size());
        setMensagensPendentes(ms.size());        
    }

    public Integer getMensagensPendentes() {
        return mensagensPendentes;
    }

    public void setMensagensPendentes(Integer mensagensPendentes) {
        this.mensagensPendentes = mensagensPendentes;
    }

    public ConversasBean getConversasBean() {
        return conversasBean;
    }

    public void setConversasBean(ConversasBean conversasBean) {
        this.conversasBean = conversasBean;
    }

    private List<Mensagem> listaEnviadoPorRemetente(List<Mensagem> mensagens, Usuario remetente) {
        List<Mensagem> msgs = new ArrayList<Mensagem>();
        for (Mensagem mens : mensagens) {
            if ((mens.getStatusMensagem().equals(StatusMensagem.Enviado))
                    && (mens.getRemetente().equals(remetente))) {
                msgs.add(mens);
            }
        }
        return msgs;
    }

    private List<Mensagem> listaPorTipoRemetenteDestinatario(List<Mensagem> mensagens, Usuario destinatario) {
        List<Mensagem> msgs = new ArrayList<Mensagem>();
        for (Mensagem mens : mensagens) {
            if ((mens.getDestinatario().equals(destinatario)) || (mens.getRemetente().equals(destinatario))
                    || ((mens.getRemetente().getTipoUsuario().equals(TipoUsuario.Consultor)) && (mens.getDestinatario().equals(destinatario)))) {
                msgs.add(mens);
            }
        }
        return msgs;
    }
    
    private List<Mensagem> listaParaCliente(List<Mensagem> mensagens, Usuario destinatario) {
        List<Mensagem> msgs = new ArrayList<Mensagem>();
        for (Mensagem mens : mensagens) {
            if ((mens.getRemetente().equals(destinatario)) || (mens.getDestinatario().equals(destinatario))) {
                msgs.add(mens);
            }
        }
        return msgs;
    }

}
