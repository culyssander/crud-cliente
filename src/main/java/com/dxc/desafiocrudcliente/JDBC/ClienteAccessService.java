package com.dxc.desafiocrudcliente.JDBC;

import com.dxc.desafiocrudcliente.JPA.ClienteNotFoundException;
import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteAccessService {

    private JdbcTemplate jdbcTemplate;

    public ClienteAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cliente> lista() {
        String sql  = "SELECT * FROM Cliente";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }


    public Cliente buscarPeloId(Long id) throws Throwable {
        String sql = "SELECT * FROM Cliente WHERE id = ?";

//        return (Cliente) jdbcTemplate.queryForObject(sql, new ClienteRowMapper(), id);

        return (Cliente) jdbcTemplate.query(sql, new ClienteRowMapper(), id)
                .stream()
                .findFirst()
                .orElseThrow(ClienteNotFoundException::new);
    }

    public int adicionar(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, cpf, telefone, endereco) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEndereco()
                );
    }

}
