package br.com.genericsfx.dao;

import br.com.genericsfx.model.Pessoa;
import java.util.List;

import javax.persistence.Query;

public class PessoaDao extends GenericDao<Pessoa, Integer> {

    private static PessoaDao instance;

    private PessoaDao() {
        super(Pessoa.class);
    }

    @SuppressWarnings("unchecked")
    public List<Pessoa> buscarPorNome(String nome) {
        Query query = this.em.createQuery("SELECT p FROM Pessoa p WHERE p.nome like :nome");
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    public static PessoaDao getInstance() {
        if (instance == null) {
            instance = new PessoaDao();
        }
        return instance;
    }
}
