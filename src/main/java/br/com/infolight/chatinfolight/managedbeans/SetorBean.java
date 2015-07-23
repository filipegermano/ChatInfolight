package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Setor;
import br.com.infolight.chatinfolight.persistencia.SetorDao;
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
@Named
@RequestScoped
public class SetorBean implements Serializable {
    
    @Inject
    private SetorDao setorDao;
    
    private Setor setor;

    public SetorBean() {
        setSetor(new Setor());
    }    

    public Setor getSetor() {        
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
    
    public void salvar() {                                
        setorDao.salvar(getSetor());        
        limpaCampos();
    }
    
    private void limpaCampos(){
        setor = new Setor();        
    }
    
}
