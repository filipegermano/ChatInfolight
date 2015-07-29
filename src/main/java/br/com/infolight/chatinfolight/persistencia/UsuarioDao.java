package br.com.infolight.chatinfolight.persistencia;

import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author filipe
 */
public class UsuarioDao extends Dao<Usuario> {

    public UsuarioDao() {
        super(Usuario.class);
    }

    private Usuario validaUsuario(Usuario usuario) {       
        Map<String, Object> parametros = new HashMap<String, Object>();        
        parametros.put("login", usuario.getLogin());
        parametros.put("senha", usuario.getSenha());
        parametros.put("empresa", usuario.getEmpresa());

        Usuario usuarioLogado = recuperaPorParametros(Usuario.BUSCA_POR_LOGIN_SENHA, parametros);
        
        if (usuarioLogado == null){
            if (usuario.getTipoUsuario() == null) {
                usuario.setTipoUsuario(TipoUsuario.Cliente);
            }
            salvar(usuario);
            usuarioLogado = recuperaPorParametros(Usuario.BUSCA_POR_LOGIN_SENHA, parametros);
        }
        
        return usuarioLogado;
    }

    public Usuario logar(Usuario usuario) {
        return validaUsuario(usuario);
    }

}
