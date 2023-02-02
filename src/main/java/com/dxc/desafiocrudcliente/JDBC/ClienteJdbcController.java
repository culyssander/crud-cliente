package com.dxc.desafiocrudcliente.JDBC;


import com.dxc.desafiocrudcliente.dominio.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jdbc/clientes")
public class ClienteJdbcController {

    private ClienteAccessService clienteAccessService;

    public ClienteJdbcController(ClienteAccessService clienteAccessService) {
        this.clienteAccessService = clienteAccessService;
    }

    @GetMapping
    public List<Cliente> lista() {
        return clienteAccessService.lista();
    }

    @GetMapping("/{id}")
    public Cliente buscaPeloId(@PathVariable Long id) throws Throwable {
        return clienteAccessService.buscarPeloId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int adiciona(@RequestBody Cliente cliente) {
        return clienteAccessService.adicionar(cliente);
    }
}
