package com.dxc.desafiocrudcliente.JPA;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void existsByCpf() {
        Cliente cliente = getCliente();
        String cpf = "989.511.456-70";

        clienteRepository.save(cliente);

        boolean resultado = clienteRepository.existsByCpf(cpf);

        Assertions.assertThat(resultado).isTrue();
    }

    @Test
    void notExistsByCpf() {
        String cpf = "989.511.456-70";

        boolean resultado = clienteRepository.existsByCpf(cpf);

        Assertions.assertThat(resultado).isFalse();
    }

    private Cliente getCliente() {
        return new Cliente(1L, "QUitumba Ferreira", "989.511.456-70", "00 00000-0000", "Brasil");
    }
}