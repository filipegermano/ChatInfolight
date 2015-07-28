package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@ApplicationScoped
@Named
public class ClientesBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    private List<Usuario> usuarios;
    private Usuario usuarioPadrao;

    @PostConstruct
    public void init() {
        setUsuarios(usuarioDao.listar(Usuario.LISTA_TODOS_CLIENTES));
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("id", 1l);
        setUsuarioPadrao(usuarioDao.recuperaPorParametros(Usuario.RECUPERA_POR_ID, parametros));
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioPadrao() {
        return usuarioPadrao;
    }

    public void setUsuarioPadrao(Usuario usuarioPadrao) {
        this.usuarioPadrao = usuarioPadrao;
    }

}
