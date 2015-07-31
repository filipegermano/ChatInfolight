package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.Status;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@RequestScoped
@Named
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private EmpresaDao empresaDao;

    private Usuario usuario;
    private String cnpjEmpresa;

    public UsuarioBean() {
        setUsuario(new Usuario());
    }

    public void salvar() {
        if (getUsuario().getLogin() != null) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("cnpj", getCnpjEmpresa());
            parametros.put("login", getUsuario().getLogin());

            Usuario usuarioBd = usuarioDao.recuperaPorParametros(Usuario.BUSCA_POR_LOGIN_EMPRESA, parametros);

            if (usuarioBd == null) {                
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("cnpj", getCnpjEmpresa());                
                System.out.println("cnpj > "+getCnpjEmpresa());
                
                getUsuario().setEmpresa(empresaDao.recuperaPorParametros(Empresa.RECUPERA_POR_CNPJ, param));
                getUsuario().setStatus(Status.Ativo);
                getUsuario().setTipoUsuario(TipoUsuario.Cliente);
                usuarioDao.salvar(getUsuario());
            } else {
                usuarioDao.alterar(usuarioBd);
            }            
        }
    }
    
    public void salvaEmpresaUsuario(){
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

}
