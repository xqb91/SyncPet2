/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.modelo.TipoAlergia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Victor Manuel Araya
 */
public class ControladorClasico {
    EntityManager em;
    EntityManagerFactory emf;
    EntityTransaction tx;

    public ControladorClasico(EntityManagerFactory emf) {
        this.emf = emf;
        em = emf.createEntityManager();
        this.tx = em.getTransaction();
    }
    
    public void editar(TipoAlergia tp)
    {
        tx.begin();
        em.merge(tp);
        tx.commit();
    }
    
}
