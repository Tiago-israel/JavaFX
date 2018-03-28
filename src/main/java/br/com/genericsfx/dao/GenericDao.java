/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.genericsfx.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author tiago
 * @param <T>
 * @param <V>
 */
public abstract class GenericDao<T, V> {

    private EntityManagerFactory emf;

    protected EntityManager em;

    private Class<T> classe;

    private String nomeClasse;

    protected GenericDao(Class<T> classe) {
        this.obterInformacoesClasse(classe);
        this.criarConexao();
    }

    public T save(T entity) {
        em.getTransaction().begin();
        T response = em.merge(entity);
        em.getTransaction().commit();
        return response;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Query query = em.createQuery("SELECT e FROM " + this.nomeClasse + " e");
        return query.getResultList();
    }

    public T findById(V id) {
        return em.find(this.classe, id);
    }

    public long count() {
        Query query = em.createQuery("SELECT COUNT(e) FROM " + this.nomeClasse + " e");
        return (long) query.getSingleResult();
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    private void obterInformacoesClasse(Class<T> classe) {
        this.classe = classe;
        this.nomeClasse = this.classe.getSimpleName();
    }

    private void criarConexao() {
        this.emf = Persistence.createEntityManagerFactory("GenericsFXPU");
        this.em = emf.createEntityManager();
    }
}
