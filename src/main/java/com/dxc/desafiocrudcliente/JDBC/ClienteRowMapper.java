package com.dxc.desafiocrudcliente.JDBC;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper {
    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Cliente(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getString("endereco")
        );
    }
}
