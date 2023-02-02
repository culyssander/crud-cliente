package com.dxc.desafiocrudcliente.JPA;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClienteRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
    public List<Cliente> consultaPeloNome(String nome) {
        String sql = "FROM Cliente WHERE nome LIKE :nome";
        return entityManager.createQuery(sql, Cliente.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }
}
