package com.dxc.desafiocrudcliente.JPA;

import com.dxc.desafiocrudcliente.dominio.Cliente;
import com.dxc.desafiocrudcliente.exception.ClienteNegocioException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    private ClienteService clienteService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    private void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteService(clienteRepository);
    }

    @AfterEach
    private void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void lista() {
        clienteService.lista();

        Mockito.verify(clienteRepository).findAll();
    }

    @Test
    void buscarPeloIdComSucesso() {
        Long id = 1L;
        given(clienteRepository.findById(id)).willReturn(Optional.of(getCliente()));

        Cliente cliente = clienteService.buscaPeloId(id);

        Assertions.assertThat(cliente.getId()).isEqualTo(id);
        Assertions.assertThat(cliente.getCpf()).isEqualTo("111.111.1111-00");
    }

    @Test
    void daraThrowPorNaoEncontraCliente() {
        Assertions.assertThatThrownBy(() ->clienteService.buscaPeloId(anyLong()))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void adicionaComSucesso() {
        Cliente cliente = getCliente();

        clienteService.adiciona(cliente);

        ArgumentCaptor<Cliente> clienteArgumentCaptor = ArgumentCaptor.forClass(Cliente.class);

        verify(clienteRepository).save(clienteArgumentCaptor.capture());

        Cliente capturedCliente = clienteArgumentCaptor.getValue();

        Assertions.assertThat(capturedCliente).isEqualTo(cliente);
    }

    @Test
    void daraThrowAdicionarPorJaExistirCPFNoBanco() {
        given(clienteRepository.existsByCpf(anyString())).willReturn(true);

        Assertions.assertThatThrownBy(() -> clienteService.adiciona(getCliente()))
                .isInstanceOf(ClienteNegocioException.class);

        verify(clienteRepository, never()).save(any());
    }

    private Cliente getCliente() {
        return new Cliente(1L, "QUitumba Ferreira", "989.511.456-70", "00 00000-0000", "Brasil");
    }

    @Test
    void alteraComSucesso() {
        Cliente cliente = new Cliente(1L, "James Gosling", "989.511.456-30", "00 1234-0000", "EUA");

        given(clienteRepository.findById(anyLong())).willReturn(Optional.of(getCliente()));

        clienteService.altera(1L, cliente);

        ArgumentCaptor<Cliente> clienteArgumentCaptor = ArgumentCaptor.forClass(Cliente.class);

        verify(clienteRepository).save(clienteArgumentCaptor.capture());

        Cliente capturedCliente = clienteArgumentCaptor.getValue();

        Assertions.assertThat(capturedCliente).isEqualTo(cliente);
    }

    @Test
    void daraThrowTentandoAlterarClienteQueNaoExiste() {
        Cliente cliente = getCliente();

        Assertions.assertThatThrownBy(() -> clienteService.altera(anyLong(), cliente))
                .isInstanceOf(ClienteNotFoundException.class);

        verify(clienteRepository, never()).save(any());
    }

    @Test
    void removeComSucesso() {
        given(clienteRepository.findById(anyLong())).willReturn(Optional.of(getCliente()));

        clienteService.remove(anyLong());

        verify(clienteRepository).deleteById(anyLong());
    }

    @Test
    void daraThrowTentandoRemoveClienteQueNaoExiste() {
        Assertions.assertThatThrownBy(() -> clienteService.remove(anyLong()))
                .isInstanceOf(ClienteNotFoundException.class);

        verify(clienteRepository, never()).deleteById(anyLong());
    }
}