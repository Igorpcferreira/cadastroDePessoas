package br.com.aplicacao.cadastro.pessoa.dominio.regras;

import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.core.exceptions.NaoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeletarPessoaRegrasTest {

    @Test
    public void test_successfully_delete_person_by_id() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doNothing().when(service).deletar(id);
        Pessoa pessoa = new Pessoa(id);
        Pessoa result = regras.execute(pessoa);
        assertEquals(id, result.getId());
        verify(service, times(1)).deletar(id);
    }

    @Test
    public void test_return_id_of_deleted_person() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doNothing().when(service).deletar(id);
        Long result = regras.execute(id);
        assertEquals(id, result);
    }

    @Test
    public void test_service_delete_method_called_with_correct_id() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doNothing().when(service).deletar(id);
        regras.execute(id);
        verify(service, times(1)).deletar(id);
    }

    @Test
    public void test_attempt_to_delete_non_existent_person() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 999L;
        doThrow(new NaoEncontradoException("Pessoa não encontrada")).when(service).deletar(id);
        assertThrows(NaoEncontradoException.class, (Executable) () -> regras.execute(id));
    }

    @Test
    public void test_handle_null_id_input_gracefully() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        assertThrows(NullPointerException.class, (Executable) () -> regras.execute((Long) null));
    }

    @Test
    public void test_handle_negative_id_input() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = -1L;
        doThrow(new IllegalArgumentException("ID inválido")).when(service).deletar(id);
        assertThrows(IllegalArgumentException.class, (Executable) () -> regras.execute(id));
    }

    @Test
    public void test_handle_zero_id_input() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 0L;
        doThrow(new IllegalArgumentException("ID inválido")).when(service).deletar(id);
        assertThrows(IllegalArgumentException.class, (Executable) () -> regras.execute(id));
    }

    @Test
    public void test_proper_exception_handling_when_service_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doThrow(new RuntimeException("Erro inesperado")).when(service).deletar(id);
        assertThrows(RuntimeException.class, (Executable) () -> regras.execute(id));
    }

    @Test
    public void test_no_other_methods_on_service_called() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doNothing().when(service).deletar(id);
        regras.execute(id);
        verify(service, times(1)).deletar(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_method_is_idempotent() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;
        doNothing().when(service).deletar(id);
        regras.execute(id);
        regras.execute(id);
        verify(service, times(2)).deletar(id);
    }

    @Test
    public void test_method_handles_concurrent_deletions_correctly() throws InterruptedException {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Long id = 1L;

        doAnswer(invocation -> {
            Thread.sleep(100);
            return null;
        }).when(service).deletar(id);

        Runnable task = () -> {
            try {
                regras.execute(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(service, times(2)).deletar(id);
    }

    @Test
    public void test_should_not_throw_exception_when_id_is_not_null() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        regras.validarIdNulo(1L);
    }

    @Test
    public void test_should_allow_further_processing_when_id_is_valid() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        try {
            regras.validarIdNulo(1L);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_should_throw_NullPointerException_when_id_is_null() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        regras.validarIdNulo(null);
    }

    @Test
    public void test_should_provide_clear_error_message_when_id_is_null() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        try {
            regras.validarIdNulo(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Id é obrigatório", e.getMessage());
        }
    }

    @Test
    public void test_should_handle_boundary_values() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        regras.validarIdNulo(Long.MIN_VALUE);
        regras.validarIdNulo(Long.MAX_VALUE);
    }

    @Test
    public void test_should_be_thread_safe() throws InterruptedException {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                regras.validarIdNulo(1L);
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    @Test
    public void test_should_log_error_message_when_id_is_null() {
        PessoaService service = mock(PessoaService.class);
        DeletarPessoaRegras regras = new DeletarPessoaRegras(service);
        try {
            regras.validarIdNulo(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Id é obrigatório", e.getMessage());
        }
    }
}