package com.dxc.desafiocrudcliente.JDBC;

import com.dxc.desafiocrudcliente.JPA.ClienteNotFoundException;
import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteAccessServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ClienteRowMapper clienteRowMapper;
    private ClienteAccessService clienteAccessService;

    @BeforeEach
    void setUp() {
        clienteAccessService = new ClienteAccessService(jdbcTemplate);
    }

    @Test
    void lista() {
        mockQuery(null);
        List<Cliente> lista = clienteAccessService.lista();
        assertPrimeiroCliente(lista.get(0));
        assertSegundoCliente(lista.get(1));
    }

    @Test
    void buscarPeloIdComSucesso() throws Throwable {
        Long id = 1L;
        mockQuery(id);
        Cliente cliente = clienteAccessService.buscarPeloId(id);
        assertPrimeiroCliente(cliente);
    }

    @Test
    void daraThrowPorqueNaoEncontroCliente() {
        Long id = 1L;
        Assertions.assertThatThrownBy(() -> clienteAccessService.buscarPeloId(id))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void adiciona() {
//        String sql =
        when(jdbcTemplate.update(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenAnswer(inv -> {
                    return 1;
                });

        int resultado = clienteAccessService.adicionar(getCliente());

        Assertions.assertThat(resultado).isEqualTo(1);
    }

    private void mockQuery(Long id) {
        if (id != null) {
            when(jdbcTemplate.query(ArgumentMatchers.anyString(), ArgumentMatchers.any(RowMapper.class), ArgumentMatchers.anyLong()))
                    .thenAnswer(ClienteAccessServiceTest::invocationOnMock);
        } else {
            when(jdbcTemplate.query(ArgumentMatchers.anyString(), ArgumentMatchers.any(RowMapper.class)))
                    .thenAnswer(ClienteAccessServiceTest::invocationOnMock);
        }
    }

    private static List<Cliente> invocationOnMock (InvocationOnMock invo) throws SQLException {
        RowMapper<Cliente> rowMapper = (RowMapper<Cliente>) invo.getArgument(1);

        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getLong(ArgumentMatchers.eq("id"))).thenReturn(1L, 2L);
        Mockito.when(rs.getString(ArgumentMatchers.eq("nome"))).thenReturn("Quitumba Ferreira", "Sansao Vieira");
        Mockito.when(rs.getString(ArgumentMatchers.eq("cpf"))).thenReturn("989.511.456-70", "989.511.456-30");
        Mockito.when(rs.getString(ArgumentMatchers.eq("telefone"))).thenReturn("00 1234-5667", "00 9876-5432");
        Mockito.when(rs.getString(ArgumentMatchers.eq("endereco"))).thenReturn("Brasil", "London");

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(rowMapper.mapRow(rs, 0));
        clientes.add(rowMapper.mapRow(rs, 1));

        return clientes;
    }

    private void assertPrimeiroCliente(Cliente cliente) {
        Assertions.assertThat(cliente.getId()).isEqualTo(1L);
        Assertions.assertThat(cliente.getNome()).isEqualTo("Quitumba Ferreira");
        Assertions.assertThat(cliente.getCpf()).isEqualTo("989.511.456-70");
        Assertions.assertThat(cliente.getTelefone()).isEqualTo("00 1234-5667");
        Assertions.assertThat(cliente.getEndereco()).isEqualTo("Brasil");
    }

    private void assertSegundoCliente(Cliente cliente) {
        Assertions.assertThat(cliente.getId()).isEqualTo(2L);
        Assertions.assertThat(cliente.getNome()).isEqualTo("Sansao Vieira");
        Assertions.assertThat(cliente.getCpf()).isEqualTo("989.511.456-30");
        Assertions.assertThat(cliente.getTelefone()).isEqualTo("00 9876-5432");
        Assertions.assertThat(cliente.getEndereco()).isEqualTo("London");
    }

    private Cliente getCliente() {
        return new Cliente(1L, "QUitumba Ferreira", "989.511.456-70", "00 00000-0000", "Brasil");
    }


}