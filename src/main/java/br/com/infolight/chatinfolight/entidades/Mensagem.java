package br.com.infolight.chatinfolight.entidades;

import br.com.infolight.chatinfolight.enums.StatusMensagem;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author filipe
 */
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "Mensagem.listaTodas",
            query = "SELECT m FROM Mensagem m ORDER BY m.dataEnvio"),
    @NamedQuery(name = "Mensagem.listaPorDestinatario",
            query = "SELECT m FROM Mensagem m WHERE (m.remetente = :remetente OR m.destinatario = :remetente) AND (m.remetente = :destinatario OR m.destinatario = :destinatario) ORDER BY m.id"),
    @NamedQuery(name = "Mensagem.listaPorLogadoRemetenteDestinatario",
            query = "SELECT m FROM Mensagem m WHERE (m.destinatario = :destinatario or m.remetente = :destinatario) or (m.remetente.tipoUsuario = 'Consultor' and m.destinatario = :destinatario) ORDER BY m.dataEnvio"),
    @NamedQuery(name = "Mensagem.listaParaCliente",
            query = "SELECT m FROM Mensagem m WHERE (m.remetente = :destinatario OR m.destinatario = :destinatario) ORDER BY m.dataEnvio"),
    @NamedQuery(name = "Mensagem.listaEnviadoPorRemetente",
            query = "SELECT m FROM Mensagem m WHERE (m.statusMensagem = 'Enviado') AND (m.remetente = :remetente)")
        
})
public class Mensagem implements Serializable, Comparable<Mensagem> {

    private static final long serialVersionUID = 1L;
    
    @Transient
    public static final String LISTA_TODAS_MENSAGENS = "Mensagem.listaTodas";
    @Transient
    public static final String LISTA_ENVIADAS_POR_REMETENTE = "Mensagem.listaEnviadoPorRemetente";
    @Transient
    public static final String LISTA_POR_LOGADO_REMETENTE_DESTINATARIO = "Mensagem.listaPorLogadoRemetenteDestinatario";
    @Transient
    public static final String LISTA_POR_DESTINATARIO = "Mensagem.listaPorDestinatario";
    @Transient
    public static final String LISTA_PARA_CLIENTE = "Mensagem.listaParaCliente";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String mensagem;
    @ManyToOne
    private Usuario destinatario;
    @ManyToOne
    private Usuario remetente;
    @Temporal(TemporalType.TIMESTAMP)    
    private Calendar dataEnvio = new GregorianCalendar();
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataRecebimento;
    @Enumerated(EnumType.STRING)
    private StatusMensagem statusMensagem = StatusMensagem.Enviado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Calendar getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Calendar dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Calendar getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Calendar dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public StatusMensagem getStatusMensagem() {
        return statusMensagem;
    }

    public void setStatusMensagem(StatusMensagem statusMensagem) {
        this.statusMensagem = statusMensagem;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mensagem other = (Mensagem) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public int compareTo(Mensagem t) {
        if (this.dataEnvio.getTimeInMillis() < t.dataEnvio.getTimeInMillis()){
            return -1;
        }
        if (this.dataEnvio.getTimeInMillis() > t.dataEnvio.getTimeInMillis()){
            return 1;
        }
        return 0;
    }
}
