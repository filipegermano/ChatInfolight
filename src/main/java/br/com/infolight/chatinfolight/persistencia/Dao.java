package br.com.infolight.chatinfolight.persistencia;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class Dao<T> implements Serializable {

    private final Class<T> classe;

    @Inject
    private EntityManager manager;

    public Dao(Class<T> classe) {
        this.classe = classe;
    }

    public void salvar(T novo) {
        manager.getTransaction().begin();
        manager.persist(novo);
        manager.getTransaction().commit();
    }

    public void alterar(T novo) {
        manager.getTransaction().begin();
        manager.merge(novo);
        manager.getTransaction().commit();
    }

    public void remover(T id) {
        manager.getTransaction().begin();
        manager.remove(id);
        manager.getTransaction().commit();
    }
    
    public T buscaPorId(Long id){
        return manager.find(classe, id);
    }

    public List<T> listar(String namedQuery) {
//        manager.getTransaction().begin();
        List<T> lista = manager.createNamedQuery(namedQuery).getResultList();
//        manager.getTransaction().commit();
        return lista;
    }

    public List<T> listar(String namedQuery, Map<String, Object> parametros) {
//        manager.getTransaction().begin();
        Query query = manager.createNamedQuery(namedQuery);
        if (parametros != null) {
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                String param = entry.getKey();
                Object valor = entry.getValue();
                query.setParameter(param, valor);
            }
        }
//        manager.getTransaction().commit();
        return query.getResultList();
    }
    
    public T recuperaPorParametros(String namedQuery, Map<String, Object> parametros) {
        Query query = manager.createNamedQuery(namedQuery);
        if (parametros != null) {
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                String param = entry.getKey();
                Object valor = entry.getValue();
                query.setParameter(param, valor);
            }
        }
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
