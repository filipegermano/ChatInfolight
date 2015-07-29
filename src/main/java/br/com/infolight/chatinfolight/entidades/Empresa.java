package br.com.infolight.chatinfolight.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
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
    @NamedQuery(name = "Empresa.recuperaPorCnpj", query = "SELECT e FROM Empresa e WHERE e.cnpj = :cnpj")
})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Transient
    public static final String RECUPERA_POR_CNPJ = "Empresa.recuperaPorCnpj";

    @Id
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String municipio;
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

}
