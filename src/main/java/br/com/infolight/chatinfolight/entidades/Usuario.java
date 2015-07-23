package br.com.infolight.chatinfolight.entidades;

import br.com.infolight.chatinfolight.enums.Status;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author filipe
 */
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "Usuario.buscaPorLoginSenha",
            query = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha"),
    @NamedQuery(name = "Usuario.listaClientes",
            query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.recuperaPorId",
            query = "SELECT u FROM Usuario u WHERE u.id = :id")
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    public static final String BUSCA_POR_LOGIN_SENHA = "Usuario.buscaPorLoginSenha";
    @Transient
    public static final String LISTA_TODOS_CLIENTES = "Usuario.listaClientes";
    @Transient
    public static final String RECUPERA_POR_ID = "Usuario.recuperaPorId";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String nome;
    private String senha;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Setor setor;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
