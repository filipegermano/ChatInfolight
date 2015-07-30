package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Mensagem;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.StatusMensagem;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import br.com.infolight.chatinfolight.persistencia.MensagemDao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
        setUsuarios(clientesBean.getUsuarios());
    }

    public void salvar(Usuario usuarioLogado, Usuario destinatario) {
        getMensagem().setRemetente(usuarioLogado);

        if (usuarioLogado.getTipoUsuario().name().equals(TipoUsuario.Consultor.name())) {
            getMensagem().setDestinatario(destinatario);
        } else {
            Usuario consultorPadrao = clientesBean.getUsuarioPadrao();
            getMensagem().setDestinatario(consultorPadrao);
        }
        if ((getMensagem().getDestinatario() != null) && (getMensagem().getRemetente() != null) && (!getMensagem().getMensagem().equals(""))) {
            getConversasBean().getConversa().add(getMensagem());
            setMensagem(new Mensagem());
        }
    }

    public void preparaConversa(Usuario remetente, Usuario destinatario) {
        Usuario consultorPadrao = clientesBean.getUsuarioPadrao();

        if (remetente.getTipoUsuario().name().equals(TipoUsuario.Consultor.name())) {
            if (destinatario != null) {
                getDestinatarioBean().setDestinatario(destinatario);
            }

            List<Mensagem> mensagensPendentes = listaEnviadoPorRemetente(getConversasBean().getConversa(), destinatario);
            for (Mensagem msg : mensagensPendentes) {
                msg.setStatusMensagem(StatusMensagem.Entregue);
                msg.setDataRecebimento(new GregorianCalendar());
            }
            if ((destinatario.getId() != null) && (remetente.getId() != null)) {
                setMensagens(listaPorTipoRemetenteDestinatario(getConversasBean().getConversa(), destinatario));
                Collections.sort(getMensagens());
            }
        } else {
            getDestinatarioBean().setDestinatario(consultorPadrao);
            List<Mensagem> mensagensPendentes = listaEnviadoPorRemetente(getConversasBean().getConversa(), consultorPadrao);
            for (Mensagem msg : mensagensPendentes) {
                msg.setStatusMensagem(StatusMensagem.Entregue);
                msg.setDataRecebimento(new GregorianCalendar());
            }
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
        List<Mensagem> ms = listaEnviadoPorRemetente(getConversasBean().getConversa(), remetente);
        System.out.println("Enviados = " + ms.size());
        setMensagensPendentes(ms.size());
    }

    public void salvarConversas() {
        List<Mensagem> mensagensParaGravar = getConversasBean().getConversa();
        if (mensagensParaGravar != null && mensagensParaGravar.size() > 0) {
            for (Mensagem conversa : mensagensParaGravar) {
                if (!conversa.getStatusMensagem().equals(StatusMensagem.Historico)) {
                    conversa.setStatusMensagem(StatusMensagem.Historico);
                    mensagemDao.salvar(conversa);
                }
            }
        }
    }

    public void removerConversaLista() {
        List<Mensagem> mensagensParaGravar = getConversasBean().getConversa();
        if (mensagensParaGravar != null && mensagensParaGravar.size() > 0) {
            for (Mensagem conversa : mensagensParaGravar) {
                Calendar diaAnterior = new GregorianCalendar();
                if (conversa.getDataEnvio().getTimeInMillis() <= (diaAnterior.getTimeInMillis() - 86400000l)
                        && (conversa.getStatusMensagem().equals(StatusMensagem.Historico))) {
                    getConversasBean().getConversa().remove(conversa);
                }

            }
        }
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

    public String formataDataHora(Calendar data) {
        String dataHoraFormatada;
        String formato = "dd.MM.yyyy HH:mm";

        SimpleDateFormat format = new SimpleDateFormat(formato);

        dataHoraFormatada = format.format(data.getTime());

        return dataHoraFormatada;
    }

}
