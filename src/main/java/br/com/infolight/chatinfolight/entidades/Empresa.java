package br.com.infolight.chatinfolight.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author filipe
 */
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "Empresa.recuperaPorCnpj", query = "SELECT e FROM Empresa e WHERE e.cnpj = :cnpj"),
    @NamedQuery(name = "Empresa.listaTodas", query = "SELECT e FROM Empresa e")
})
public class Empresa implements Serializable, Identificavel {
    private static final long serialVersionUID = 1L;
    
    @Transient
    public static final String RECUPERA_POR_CNPJ = "Empresa.recuperaPorCnpj";
    @Transient
    public static final String LISTA_TODAS = "Empresa.listaTodas";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String razaoSocial;
    @Column(length = 150)
    private String nomeFantasia;
    @Column(length = 20)
    private String cnpj;
    @Column(length = 100)
    private String municipio;
    @Column(length = 2)
    private String uf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public Long getIdentificador() {
        return getId();
    }

}
