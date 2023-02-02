package com.dxc.desafiocrudcliente.JPA;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jpa/clientes")
@Tag(name = "ClienteJPAController", description = "CRUD do cliente com JPA")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> lita() {
        return clienteService.lista();
    }

    @GetMapping("/nome")
    public List<Cliente> buscaPeloNome(String nome) {
        return clienteService.buscaPeloNome(nome);
    }


    @GetMapping("/{id}")
    public Cliente buscaPeloId(@PathVariable Long id) {
        return clienteService.buscaPeloId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adiciona(@Validated @RequestBody Cliente cliente) {
        return clienteService.adiciona(cliente);
    }

    @PutMapping("/{id}")
    public Cliente altera(@PathVariable Long id, @Validated @RequestBody Cliente cliente) {
        return clienteService.altera(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        clienteService.remove(id);
    }
}
