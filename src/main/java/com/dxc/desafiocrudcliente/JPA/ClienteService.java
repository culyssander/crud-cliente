package com.dxc.desafiocrudcliente.JPA;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import com.dxc.desafiocrudcliente.exception.ClienteNegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> lista() {
        return clienteRepository.findAll();
    }

    public List<Cliente> buscaPeloNome(String nome) {
        return clienteRepository.consultaPeloNome(nome);
    }

    public Cliente buscaPeloId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException());
    }

    public Cliente adiciona(Cliente cliente) {

        if(clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new ClienteNegocioException();
        }

        return clienteRepository.save(cliente);
    }

    public Cliente altera(Long id, Cliente cliente) {
        Cliente clienteActual = buscaPeloId(id);

        BeanUtils.copyProperties(cliente, clienteActual, "id");

        return clienteRepository.save(clienteActual);
    }

    public void remove(Long id) {
        Cliente clienteActual = buscaPeloId(id);
        clienteRepository.deleteById(id);
    }
}
