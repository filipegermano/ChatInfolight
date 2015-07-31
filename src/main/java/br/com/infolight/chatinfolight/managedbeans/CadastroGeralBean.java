package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.Status;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@RequestScoped
@Named
public class CadastroGeralBean implements Serializable {

    @Inject
    private EmpresaDao empresaDao;
    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private ClientesBean clientesBean;
    private Usuario usuario;
    private Empresa empresa;

    public CadastroGeralBean() {
        setEmpresa(new Empresa());
        setUsuario(new Usuario());
    }

    public void cadastrar() {

        if (getEmpresa().getCnpj() != null) {
            Map<String, Object> parametrosEmpresa = new HashMap<String, Object>();
            parametrosEmpresa.put("cnpj", getEmpresa().getCnpj());

            Empresa empresaBd = empresaDao.recuperaPorParametros(Empresa.RECUPERA_POR_CNPJ, parametrosEmpresa);

            if (empresaBd == null) {
                empresaDao.salvar(getEmpresa());
            } else {
                empresaDao.alterar(empresaBd);
                setEmpresa(empresaBd);
            }
        }

        if (getUsuario().getLogin() != null) {
            Map<String, Object> parametrosUsuario = new HashMap<String, Object>();
            parametrosUsuario.put("cnpj", getEmpresa().getCnpj());
            parametrosUsuario.put("login", getUsuario().getLogin());

            Usuario usuarioBd = usuarioDao.recuperaPorParametros(Usuario.BUSCA_POR_LOGIN_EMPRESA, parametrosUsuario);

            if (usuarioBd == null) {
                getUsuario().setEmpresa(getEmpresa());
                getUsuario().setStatus(Status.Ativo);
                getUsuario().setTipoUsuario(TipoUsuario.Cliente);
                usuarioDao.salvar(getUsuario());
            } else {
                usuarioDao.alterar(usuarioBd);
                setUsuario(usuarioBd);
            }            
            getClientesBean().getUsuarios().add(getUsuario());
        }

        if (getUsuario().getLogin() != null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/ChatInfolight/privado/chat/conversas.xhtml?login=" + getUsuario().getLogin() + "&senha=" + getUsuario().getSenha() + "&cnpj=" + getEmpresa().getCnpj());
            } catch (IOException ex) {
                Logger.getLogger(CadastroGeralBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/ChatInfolight/");
            } catch (IOException ex) {
                Logger.getLogger(CadastroGeralBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ClientesBean getClientesBean() {
        return clientesBean;
    }

}
