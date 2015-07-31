package br.com.infolight.chatinfolight.managedbeans;

import br.com.infolight.chatinfolight.entidades.Empresa;
import br.com.infolight.chatinfolight.persistencia.EmpresaDao;
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
public class EmpresaBean implements Serializable {
    
    @Inject
    private EmpresaDao empresaDao;
    private Empresa empresa;

    public EmpresaBean() {
        setEmpresa(new Empresa());
    }

    public void salvar(){
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cnpj", getEmpresa().getCnpj());        
        
        if (getEmpresa().getCnpj() != null){
            Empresa empresaBd = empresaDao.recuperaPorParametros(Empresa.RECUPERA_POR_CNPJ, parametros);        
            
            if (empresaBd == null){
                empresaDao.salvar(getEmpresa());
            } else {
                empresaDao.alterar(empresaBd);
            }
        }
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    
}
