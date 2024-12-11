package br.com.aplicacao.cadastro.pessoa.aplicacao;

import br.com.aplicacao.cadastro.pessoa.dominio.PessoaRepository;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.core.exceptions.NaoEncontradoException;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @Test
    public void test_successfully_include_new_pessoa() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa(1L, "Nome", null, null);
        when(repository.incluir(pessoa)).thenReturn(pessoa);
        Pessoa result = service.incluir(pessoa);
        assertEquals(pessoa, result);
        verify(repository, times(1)).incluir(pessoa);
    }

    @Test
    public void test_successfully_alter_existing_pessoa() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa(1L, "Nome", null, null);
        when(repository.alterar(pessoa)).thenReturn(pessoa);
        Pessoa result = service.alterar(pessoa);
        assertEquals(pessoa, result);
        verify(repository, times(1)).alterar(pessoa);
    }

    @Test
    public void test_successfully_delete_existing_pessoa_by_id() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Long id = 1L;
        when(repository.consultar(id)).thenReturn(Optional.of(new Pessoa(id, "Nome", null, null)));
    }

    @Test
    public void test_successfully_consult_existing_pessoa_by_id() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Long id = 1L;
        Pessoa pessoa = new Pessoa(id, "Nome", null, null);
        when(repository.consultar(id)).thenReturn(Optional.of(pessoa));
        Pessoa result = service.consultar(id);
        assertEquals(pessoa, result);
        verify(repository, times(1)).consultar(id);
    }

    @Test
    public void test_validate_if_contact_is_already_used() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa(1L, "Nome", null, null);
        when(repository.contatoJaUtilizado(pessoa)).thenReturn(true);
        boolean result = service.contatoJaUtilizado(pessoa);
        assertTrue(result);
        verify(repository, times(1)).contatoJaUtilizado(pessoa);
    }

    @Test
    public void test_attempt_to_delete_non_existent_pessoa() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Long id = 999L;
        doThrow(new NaoEncontradoException("Pessoa não encontrada")).when(repository).excluir(id);
        assertThrows(NaoEncontradoException.class, () -> service.deletar(id));
    }

    @Test
    public void test_attempt_to_consult_non_existent_pessoa() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Long id = 999L;
        when(repository.consultar(id)).thenReturn(Optional.empty());
        assertThrows(NaoEncontradoException.class, () -> service.consultar(id));
    }

    @Test
    public void test_attempt_to_include_pessoa_with_null_fields() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa(null, null, null, null);
        when(repository.incluir(pessoa)).thenThrow(new IllegalArgumentException("Pessoa inválida"));
        assertThrows(IllegalArgumentException.class, () -> service.incluir(pessoa));
    }

    @Test
    public void test_attempt_to_alter_pessoa_with_invalid_data() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa(1L, null, null, null);
        when(repository.alterar(pessoa)).thenThrow(new IllegalArgumentException("Pessoa inválida"));
        assertThrows(IllegalArgumentException.class, () -> service.alterar(pessoa));
    }

    @Test
    public void test_returns_true_when_contact_is_already_used_by_another_person() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa();
        when(repository.contatoJaUtilizadoSemSerEu(pessoa)).thenReturn(true);
        assertTrue(service.contatoJaUtilizadoSemSerEu(pessoa));
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa);
    }

    @Test
    public void test_returns_false_when_contact_is_not_used_by_any_other_person() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa();
        when(repository.contatoJaUtilizadoSemSerEu(pessoa)).thenReturn(false);
        assertFalse(service.contatoJaUtilizadoSemSerEu(pessoa));
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa);
    }

    @Test
    public void test_correctly_calls_repository_method_with_given_person_object() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa();
        service.contatoJaUtilizadoSemSerEu(pessoa);
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa);
    }

    @Test
    public void test_works_with_different_valid_person_ids() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa1 = new Pessoa(1L);
        Pessoa pessoa2 = new Pessoa(2L);
        when(repository.contatoJaUtilizadoSemSerEu(pessoa1)).thenReturn(false);
        when(repository.contatoJaUtilizadoSemSerEu(pessoa2)).thenReturn(true);
        assertFalse(service.contatoJaUtilizadoSemSerEu(pessoa1));
        assertTrue(service.contatoJaUtilizadoSemSerEu(pessoa2));
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa1);
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa2);
    }

    @Test
    public void test_ensures_method_is_called_only_once_per_invocation() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa();
        service.contatoJaUtilizadoSemSerEu(pessoa);
        verify(repository, times(1)).contatoJaUtilizadoSemSerEu(pessoa);
    }

    @Test
    public void test_verifies_repository_interaction_is_as_expected() {
        PessoaRepository repository = mock(PessoaRepository.class);
        PessoaService service = new PessoaService(repository);
        Pessoa pessoa = new Pessoa();
        service.contatoJaUtilizadoSemSerEu(pessoa);
        verify(repository).contatoJaUtilizadoSemSerEu(pessoa);
    }
}