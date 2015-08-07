package br.com.infolight.chatinfolight.managedbeans.converter;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.persistencia.Dao;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author filipe
 */
@Named
@RequestScoped
public class EmpresaConverter extends Conversor<Empresa>{

    @Inject
    private EmpresaDao empresaDao;
    
    @Override
    public Dao<Empresa> getDao() {
        return empresaDao;
    }
    
}
