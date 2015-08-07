package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.entidades.Usuario;
import br.com.infolight.chatinfolight.enums.Status;
import br.com.infolight.chatinfolight.enums.TipoUsuario;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
import br.com.infolight.chatinfolight.persistencia.UsuarioDao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private Empresa empresaSelecionada;
    private List<Empresa> empresas;
    private List<Usuario> usuarios;   

    @PostConstruct
    public void init() {
        setUsuario(new Usuario());
        setEmpresas(empresaDao.listar(Empresa.LISTA_TODAS));
        setUsuarios(usuarioDao.listar(Usuario.LISTA_TODOS));
        getEmpresas().forEach(System.out::println);
    }

    public void salvar() {
        if (getUsuario().getId() == null){
            usuarioDao.salvar(getUsuario());
        } else {
            usuarioDao.alterar(getUsuario());
        }
        
    }

    public void salvaEmpresaUsuario() {

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empresa getEmpresaSelecionada() {
        return empresaSelecionada;
    }

    public void setEmpresaSelecionada(Empresa empresaSelecionada) {
        this.empresaSelecionada = empresaSelecionada;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public TipoUsuario[] getTiposUsuario() {
        return TipoUsuario.values();
    }

    public Status[] getStatuses() {
        return Status.values();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public String alterar(Usuario usuario){
                   
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/ChatInfolight/privado/usuario/form.xhtml");
            setUsuario(usuario);
        return "/privado/usuario/form.xhtml";
    }
    
    public String remover(Usuario usuario){        
        usuarioDao.remover(usuario);
        setUsuarios(usuarioDao.listar(Usuario.LISTA_TODOS));
        return "/privado/usuario/lista.xhtml";
    }

}
