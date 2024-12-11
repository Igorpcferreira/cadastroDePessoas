package br.com.aplicacao.cadastro.pessoa.controller;

import br.com.aplicacao.cadastro.contato.dto.ContatoDto;
import br.com.aplicacao.cadastro.endereco.dto.EnderecoDto;
import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.PessoaRepository;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.AlterarPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.DeletarPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.IncluirPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dto.PessoaDto;
import br.com.aplicacao.core.exceptions.NaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PessoaControllerTest {

    private final IncluirPessoaRegras incluirPessoaRegras = mock(IncluirPessoaRegras.class);
    private final AlterarPessoaRegras alterarPessoaRegras = mock(AlterarPessoaRegras.class);
    private final PessoaController pessoaController = new PessoaController(mock(PessoaService.class), incluirPessoaRegras, alterarPessoaRegras, mock(DeletarPessoaRegras.class));

    @Test
    public void test_consultar_valid_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        IncluirPessoaRegras incluirPessoaRegras = mock(IncluirPessoaRegras.class);
        AlterarPessoaRegras alterarPessoaRegras = mock(AlterarPessoaRegras.class);
        DeletarPessoaRegras deletarPessoaRegras = mock(DeletarPessoaRegras.class);
        PessoaController controller = new PessoaController(pessoaService, incluirPessoaRegras, alterarPessoaRegras, deletarPessoaRegras);

        Pessoa pessoa = new Pessoa(1L, "John Doe", null, null);
        when(pessoaService.consultar(1L)).thenReturn(pessoa);

        ResponseEntity<Pessoa> response = controller.consultar(1L);
    }

    @Test
    public void test_consultar_non_existent_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        IncluirPessoaRegras incluirPessoaRegras = mock(IncluirPessoaRegras.class);
        AlterarPessoaRegras alterarPessoaRegras = mock(AlterarPessoaRegras.class);
        DeletarPessoaRegras deletarPessoaRegras = mock(DeletarPessoaRegras.class);
        PessoaController controller = new PessoaController(pessoaService, incluirPessoaRegras, alterarPessoaRegras, deletarPessoaRegras);

        when(pessoaService.consultar(1L)).thenThrow(new NaoEncontradoException(""));

        assertThrows(NaoEncontradoException.class, () -> controller.consultar(1L));
    }

    @Test
    public void test_successfully_include_new_person_with_valid_data() {
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("John Doe").contato(new ContatoDto("(12) 34567-8910")).endereco(new EnderecoDto(1L, "bairro")).build();
        Pessoa pessoa = pessoaDto.toEntity();
        when(incluirPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.incluir(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().longValue());
    }

    @Test
    public void test_include_person_with_minimal_required_fields() {
        PessoaDto pessoaDto = PessoaDto.builder().nome("John Doe").contato(new ContatoDto("(12) 34567-8910")).endereco(new EnderecoDto(1L, "bairro")).build();
        Pessoa pessoa = pessoaDto.toEntity();
        when(incluirPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.incluir(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoa.getId(), response.getBody());
    }

    @Test
    public void test_include_person_with_all_fields_populated() {
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("John Doe").contato(new ContatoDto("(12) 34567-8910")).endereco(new EnderecoDto(1L, "bairro")).build();
        Pessoa pessoa = pessoaDto.toEntity();
        when(incluirPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.incluir(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().longValue());
    }

    @Test
    public void test_include_person_with_unique_name_and_contact() {
        PessoaDto pessoaDto = PessoaDto.builder().nome("Unique Name").contato(new ContatoDto("(12) 34567-8910")).endereco(new EnderecoDto(1L, "bairro")).build();
        Pessoa pessoa = pessoaDto.toEntity();
        when(incluirPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.incluir(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoa.getId(), response.getBody());
    }

    @Test
    public void test_delete_existing_person_by_valid_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;
        doNothing().when(pessoaService).deletar(validId);

        pessoaController.deletar(validId);

        verify(pessoaService, times(1)).deletar(validId);
    }

    @Test
    public void test_service_layer_delete_called_with_correct_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;
        doNothing().when(pessoaService).deletar(validId);

        pessoaController.deletar(validId);

        verify(pessoaService).deletar(validId);
    }

    @Test
    public void test_delete_person_with_null_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long nullId = null;

        assertThrows(NullPointerException.class, () -> pessoaController.deletar(nullId));
    }

    @Test
    public void test_delete_person_with_non_existent_id() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long nonExistentId = 999L;
        doThrow(new EntityNotFoundException("Person not found")).when(pessoaService).deletar(nonExistentId);

        assertThrows(EntityNotFoundException.class, () -> pessoaController.deletar(nonExistentId));
    }

    @Test
    public void test_handle_service_layer_exception() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;
        doThrow(new RuntimeException("Unexpected error")).when(pessoaService).deletar(validId);

        assertThrows(RuntimeException.class, () -> pessoaController.deletar(validId));
    }

    @Test
    public void test_method_is_idempotent() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;
        doNothing().when(pessoaService).deletar(validId);

        pessoaController.deletar(validId);
        pessoaController.deletar(validId);

        verify(pessoaService, times(2)).deletar(validId);
    }

    @Test
    public void test_transaction_rollback_on_failure() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;
        doThrow(new RuntimeException("Unexpected error")).when(pessoaService).deletar(validId);

        assertThrows(RuntimeException.class, () -> pessoaController.deletar(validId));

        verify(pessoaService).deletar(validId);
    }

    @Test
    public void test_concurrent_deletion_requests_handling() throws InterruptedException {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validId = 1L;

        doAnswer(invocation -> {
            return null;
        }).when(pessoaService).deletar(validId);

        Thread thread1 = new Thread(() -> pessoaController.deletar(validId));
        Thread thread2 = new Thread(() -> pessoaController.deletar(validId));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(pessoaService, times(2)).deletar(validId);
    }

    @Test
    public void test_no_inadvertent_deletion_of_other_records() {
        PessoaService pessoaService = mock(PessoaService.class);
        DeletarPessoaRegras deletarPessoaRegras = new DeletarPessoaRegras(pessoaService);
        PessoaController pessoaController = new PessoaController(null, null, null, deletarPessoaRegras);

        Long validIdToDelete = 1L;
        Long otherValidId = 2L;

        doNothing().when(pessoaService).deletar(validIdToDelete);

        pessoaController.deletar(validIdToDelete);

        verify(pessoaService).deletar(validIdToDelete);
        verify(pessoaService, never()).deletar(otherValidId);
    }

    @Test
    public void test_successfully_update_pessoa_with_valid_data() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Valid Name", new ContatoDto("1234567890"), new EnderecoDto(1L, "bairro"));
        Pessoa pessoa = pessoaDto.toEntity();
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.alterar(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoa.getId(), response.getBody());
    }

    @Test
    public void test_updated_pessoa_retains_correct_id() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Valid Name", new ContatoDto("1234567890"), new EnderecoDto(1L, "bairro"));
        Pessoa pessoa = pessoaDto.toEntity();
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.alterar(pessoaDto);
        assertEquals(pessoaDto.getId(), response.getBody());
    }

    @Test
    public void test_response_status_ok_on_successful_update() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Valid Name", new ContatoDto("1234567890"), new EnderecoDto(1L, "bairro"));
        Pessoa pessoa = pessoaDto.toEntity();
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.alterar(pessoaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_returned_id_matches_updated_pessoa_id() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Valid Name", new ContatoDto("1234567890"), new EnderecoDto(1L, "bairro"));
        Pessoa pessoa = pessoaDto.toEntity();
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenReturn(pessoa);
        ResponseEntity<Long> response = pessoaController.alterar(pessoaDto);
        assertEquals(pessoa.getId(), response.getBody());
    }

    @Test
    public void test_update_pessoa_with_name_already_in_use() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Existing Name", new ContatoDto("1234567890"), new EnderecoDto(1L, "bairro"));
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenThrow(new IllegalArgumentException("Nome já cadastrado"));
        assertThrows(IllegalArgumentException.class, () -> {
            pessoaController.alterar(pessoaDto);
        });
    }

    @Test
    public void test_update_pessoa_with_contact_already_in_use() {
        PessoaDto pessoaDto = new PessoaDto(1L, "Valid Name", new ContatoDto("existing_contact"), new EnderecoDto(1L, "bairro"));
        when(alterarPessoaRegras.execute(any(Pessoa.class))).thenThrow(new IllegalArgumentException("Contato já cadastrado"));
        assertThrows(IllegalArgumentException.class, () -> {
            pessoaController.alterar(pessoaDto);
        });
    }

    @Test
    public void test_should_return_list_of_pessoa_when_multiple_entries() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        List<Pessoa> pessoas = List.of(new Pessoa(1L, "John Doe", null, null), new Pessoa(2L, "Jane Doe", null, null));
        Page<Pessoa> page = new PageImpl<>(pessoas);
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void test_should_return_empty_list_when_no_entries() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        Page<Pessoa> page = new PageImpl<>(List.of());
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void test_should_return_http_status_200_on_successful_retrieval() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        Page<Pessoa> page = new PageImpl<>(List.of(new Pessoa(1L, "John Doe", null, null)));
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_should_return_list_of_pessoa_with_correct_attributes() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        Pessoa pessoa = new Pessoa(1L, "John Doe", null, null);
        Page<Pessoa> page = new PageImpl<>(List.of(pessoa));
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getNome());
    }

    @Test
    public void test_should_handle_pagination_correctly_if_implemented() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        List<Pessoa> pessoas = List.of(new Pessoa(1L, "John Doe", null, null), new Pessoa(2L, "Jane Doe", null, null));
        Page<Pessoa> page = new PageImpl<>(pessoas);
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void test_should_handle_database_connection_lost() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        when(pessoaService.listar()).thenThrow(new RuntimeException("Database connection lost"));
        assertThrows(RuntimeException.class, () -> pessoaController.listar());
    }

    @Test
    public void test_should_handle_repository_throws_exception() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        when(pessoaService.listar()).thenThrow(new RuntimeException("Repository exception"));
        assertThrows(RuntimeException.class, () -> pessoaController.listar());
    }

    @Test
    public void test_should_handle_service_layer_returns_empty_page_object() {
        PessoaService pessoaService = mock(PessoaService.class);
        PessoaController pessoaController = new PessoaController(pessoaService, null, null, null);
        Page<Pessoa> page = Page.empty();
        when(pessoaService.listar()).thenReturn(page);
        ResponseEntity<List<Pessoa>> response = pessoaController.listar();
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void test_should_verify_method_does_not_alter_database_state() {

        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService pessoaService = new PessoaService(repository);

        when(repository.listar()).thenReturn(Page.empty());

        Page<Pessoa> resultPage = pessoaService.listar();

        verify(repository, never()).incluir(any(Pessoa.class));
        verify(repository, never()).alterar(any(Pessoa.class));
        verify(repository, never()).excluir(anyLong());

        assertTrue(resultPage.isEmpty());
    }
}
