package br.com.aplicacao.cadastro.pessoa.dominio.regras;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class IncluirPessoaRegrasTest {

    @Test
    public void test_successfully_alters_valid_pessoa() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        when(service.incluir(pessoa)).thenReturn(pessoa);
        Pessoa result = regras.execute(pessoa);
        assertEquals(pessoa, result);
        verify(service, times(1)).incluir(pessoa);
    }

    @Test
    public void test_returns_altered_pessoa_after_successful_validation() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        when(service.incluir(pessoa)).thenReturn(pessoa);
        Pessoa result = regras.execute(pessoa);
        assertNotNull(result);
        assertEquals(pessoa, result);
    }

    @Test
    public void test_pessoa_with_null_name_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, null, null, new Contato("(12) 34567-8910"));
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
    }

    @Test
    public void test_pessoa_with_long_name_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "NameTooLongForValidation", null, new Contato("(12) 34567-8910"));
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
    }

    @Test
    public void test_pessoa_with_null_phone_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato(null));
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
    }

    @Test
    public void test_pessoa_with_invalid_phone_format_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("invalidPhone"));
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
    }

    @Test
    public void test_pessoa_with_used_contact_throws_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        when(service.contatoJaUtilizado(pessoa)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
    }

    @Test
    public void test_service_incluir_called_once_for_valid_pessoa() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        when(service.incluir(pessoa)).thenReturn(pessoa);
        regras.execute(pessoa);
        verify(service, times(1)).incluir(pessoa);
    }

    @Test
    public void test_no_alteration_if_validation_fails() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, null, null, new Contato("(12) 34567-8910"));
        assertThrows(IllegalArgumentException.class, () -> regras.execute(pessoa));
        verify(service, never()).incluir(any(Pessoa.class));
    }

    @Test
    public void test_all_validations_called_in_sequence() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = spy(new IncluirPessoaRegras(service));
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        doNothing().when(regras).validarNome(any(Pessoa.class));
        doNothing().when(regras).validarTelefoneContato(any(Pessoa.class));
        doNothing().when(regras).validarContatoJaCadastrado(any(Pessoa.class));

        when(service.incluir(any(Pessoa.class))).thenReturn(pessoa);

        regras.execute(pessoa);

        InOrder inOrder = inOrder(regras);

        inOrder.verify(regras).validarNome(any(Pessoa.class));
        inOrder.verify(regras).validarTelefoneContato(any(Pessoa.class));
        inOrder.verify(regras).validarContatoJaCadastrado(any(Pessoa.class));

        verify(service).incluir(any(Pessoa.class));
    }

    @Test
    public void test_null_name_should_throw_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, null, null, new Contato("(12) 34567-8910"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarNome(pessoa));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_name_exceeding_maximum_length_should_throw_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "12345678901", null, new Contato("(12) 34567-8910"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarNome(pessoa));
        assertEquals("O nome não deve ter mais de 10 caracteres", exception.getMessage());
    }

    @Test
    public void test_empty_string_as_name_should_throw_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "", null, new Contato("(12) 34567-8910"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarNome(pessoa));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_name_with_leading_or_trailing_spaces_should_be_trimmed_and_validated() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = spy(new IncluirPessoaRegras(service));
        Pessoa pessoa = new Pessoa(1L, "  ValidName  ", null, new Contato("(12) 34567-8910"));

        doNothing().when(regras).validarNomeNulo(any(Pessoa.class));
        doNothing().when(regras).validarTamanhoNome(any(Pessoa.class));

        regras.validarNome(pessoa);

        verify(regras).validarTamanhoNome(argThat(
                p -> p.getNome().equals("ValidName")
        ));
    }

    @Test
    public void test_name_with_only_whitespace_should_throw_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "   ", null, new Contato("(12) 34567-8910"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarNome(pessoa));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_nome_null() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, null, null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_nome_empty_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_nome_whitespace_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "   ", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_nome_valid_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "ValidName", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_special_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "@Valid#Name!", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_leading_trailing_whitespace() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "  ValidName  ", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_mixed_whitespace_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, " Val id Name ", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_non_latin_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "名字", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_numeric_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Name123", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_exception_message_for_invalid_nome() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_very_long_strings_gracefully() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        String longString = "a".repeat(1000);
        Pessoa pessoa = new Pessoa(1L, longString, null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_nome_escape_sequences() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Name\nWith\nNewlines", null, null);
        assertDoesNotThrow(() -> {
            regras.validarNomeNulo(pessoa);
        });
    }

    @Test
    public void test_name_length_exactly_10_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "1234567890", null, null);
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_name_length_less_than_10_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "123456789", null, null);
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_name_length_exceeds_10_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "12345678901", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarTamanhoNome(pessoa);
        });
        assertEquals("O nome não deve ter mais de 10 caracteres", exception.getMessage());
    }

    @Test
    public void test_name_length_after_trimming_spaces() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "  1234567890  ", null, null);
        pessoa.setNome(pessoa.getNome().trim());
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_null_name_gracefully_handled() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, null, null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_empty_string_name_gracefully_handled() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_name_with_only_spaces_after_trimming() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "          ", null, null);
        pessoa.setNome(pessoa.getNome().trim());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarNomeNulo(pessoa);
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void test_names_with_special_characters_within_length_limit() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "@#$%^&*()", null, null);
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_names_with_numbers_within_length_limit() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "1234567890", null, null);
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_names_with_mixed_case_letters_within_length_limit() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "AbCdEfGhIj", null, null);
        regras.validarTamanhoNome(pessoa);
    }

    @Test
    public void test_name_not_modified_if_within_valid_length() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        String originalName = "ValidName";
        Pessoa pessoa = new Pessoa(1L, originalName, null, null);
        regras.validarTamanhoNome(pessoa);
        assertEquals(originalName, pessoa.getNome());
    }

    @Test
    public void test_exception_message_when_name_too_long() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "12345678901", null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            regras.validarTamanhoNome(pessoa);
        });
        assertEquals("O nome não deve ter mais de 10 caracteres", exception.getMessage());
    }

    @Test
    public void test_trims_leading_spaces() {
        Pessoa pessoa = Pessoa.builder().nome("  John").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John", pessoa.getNome());
    }

    @Test
    public void test_trims_trailing_spaces() {
        Pessoa pessoa = Pessoa.builder().nome("John  ").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John", pessoa.getNome());
    }

    @Test
    public void test_retains_name_no_spaces() {
        Pessoa pessoa = Pessoa.builder().nome("John").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John", pessoa.getNome());
    }

    @Test
    public void test_handles_multiple_spaces() {
        Pessoa pessoa = Pessoa.builder().nome("  John  ").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John", pessoa.getNome());
    }

    @Test
    public void test_already_trimmed_name() {
        Pessoa pessoa = Pessoa.builder().nome("John").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John", pessoa.getNome());
    }

    @Test
    public void test_single_space_name() {
        Pessoa pessoa = Pessoa.builder().nome(" ").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("", pessoa.getNome());
    }

    @Test
    public void test_multiple_spaces_name() {
        Pessoa pessoa = Pessoa.builder().nome("     ").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("", pessoa.getNome());
    }

    @Test
    public void test_empty_string_name() {
        Pessoa pessoa = Pessoa.builder().nome("").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("", pessoa.getNome());
    }

    @Test
    public void test_whitespace_characters_name() {
        Pessoa pessoa = Pessoa.builder().nome("\t\n ").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("", pessoa.getNome());
    }

    @Test
    public void test_internal_spaces_only_name() {
        Pessoa pessoa = Pessoa.builder().nome("John Doe").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.tratarNomeComEspacosEmBranco(pessoa);
        assertEquals("John Doe", pessoa.getNome());
    }

    @Test
    public void test_no_exceptions_for_valid_inputs() {
        Pessoa pessoa = Pessoa.builder().nome("John").build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        try {
            regras.tratarNomeComEspacosEmBranco(pessoa);
            assertEquals("John", pessoa.getNome());
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void test_valid_non_null_non_empty_non_blank_phone_number() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        assertDoesNotThrow(() -> regras.validarTelefoneContato(pessoa));
    }

    @Test
    public void test_phone_number_matches_regex_pattern() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        assertDoesNotThrow(() -> regras.validarTelefoneContato(pessoa));
    }

    @Test
    public void test_processes_valid_phone_number_without_exceptions() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));
        assertDoesNotThrow(() -> regras.validarTelefoneContato(pessoa));
    }

    @Test
    public void test_phone_number_is_null() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato(null));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone é obrigatório", exception.getMessage());
    }

    @Test
    public void test_phone_number_is_empty_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato(""));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone é obrigatório", exception.getMessage());
    }

    @Test
    public void test_phone_number_is_whitespace_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("   "));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone é obrigatório", exception.getMessage());
    }

    @Test
    public void test_phone_number_does_not_match_regex_pattern() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("123456789"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    public void test_phone_number_contains_invalid_special_characters() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-891@"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    public void test_phone_number_too_short_or_too_long() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);

        Pessoa pessoaShort = new Pessoa(1L, "Valid Name", null, new Contato("(12) 3456-789"));
        Exception exceptionShort = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoaShort));
        assertEquals("Telefone inválido", exceptionShort.getMessage());

        Pessoa pessoaLong = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-89101"));
        Exception exceptionLong = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoaLong));
        assertEquals("Telefone inválido", exceptionLong.getMessage());
    }

    @Test
    public void test_phone_number_valid_characters_incorrect_format() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("12345678910"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoa));
        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    public void test_method_does_not_alter_phone_number() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        String originalPhoneNumber = "(12) 34567-8910";
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato(originalPhoneNumber));

        try {
            regras.validarTelefoneContato(pessoa);
            assertEquals(originalPhoneNumber, pessoa.getContato().getTelefone());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void test_exception_messages_are_clear_and_specific() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);

        Pessoa pessoaNullPhoneNumber = new Pessoa(1L, "Valid Name", null, new Contato(null));
        Exception exceptionNullPhoneNumber = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoaNullPhoneNumber));
        assertEquals("Telefone é obrigatório", exceptionNullPhoneNumber.getMessage());

        Pessoa pessoaInvalidFormatPhoneNumber = new Pessoa(1L, "Valid Name", null, new Contato("123456789"));
        Exception exceptionInvalidFormatPhoneNumber = assertThrows(IllegalArgumentException.class, () -> regras.validarTelefoneContato(pessoaInvalidFormatPhoneNumber));
        assertEquals("Telefone inválido", exceptionInvalidFormatPhoneNumber.getMessage());
    }

    @Test
    public void test_telefone_non_null_non_empty_non_blank() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato("1234567890")).build();
        regras.verificarTelefoneNulo(pessoa);
    }

    @Test
    public void test_telefone_valid_format() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato("123-456-7890")).build();
        regras.verificarTelefoneNulo(pessoa);
    }

    @Test
    public void test_telefone_null() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato(null)).build();
        assertThrows(IllegalArgumentException.class, () -> regras.verificarTelefoneNulo(pessoa));
    }

    @Test
    public void test_telefone_empty_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato("")).build();
        assertThrows(IllegalArgumentException.class, () -> regras.verificarTelefoneNulo(pessoa));
    }

    @Test
    public void test_telefone_whitespace_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato("   ")).build();
        assertThrows(IllegalArgumentException.class, () -> regras.verificarTelefoneNulo(pessoa));
    }

    @Test
    public void test_pessoa_null() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        assertThrows(NullPointerException.class, () -> regras.verificarTelefoneNulo(null));
    }

    @Test
    public void test_contato_null() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(null).build();
        assertThrows(NullPointerException.class, () -> regras.verificarTelefoneNulo(pessoa));
    }

    @Test
    public void test_telefone_numeric_string() {
        PessoaService pessoaServiceMock = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(pessoaServiceMock);
        Pessoa pessoa = Pessoa.builder().contato(new Contato("1234567890")).build();
        regras.verificarTelefoneNulo(pessoa);
    }

    @Test
    public void test_valid_phone_number_with_correct_format_is_accepted() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 3456-7890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test
    public void test_phone_number_with_parentheses_and_hyphen_in_correct_positions_is_accepted() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 34567-8901")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test
    public void test_phone_number_with_4_digits_in_first_part_and_4_digits_in_second_part_is_accepted() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 3456-7890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test
    public void test_phone_number_with_5_digits_in_first_part_and_4_digits_in_second_part_is_accepted() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 34567-8901")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_missing_parentheses_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("12 3456-7890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_missing_hyphen_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 34567890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_more_than_5_digits_in_first_part_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 345678-9012")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_fewer_than_4_digits_in_second_part_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 3456-789")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_letters_or_special_characters_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12) 3456-78a0")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_extra_spaces_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("(12)  3456-7890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_only_digits_and_no_formatting_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato("1234567890")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_phone_number_with_leading_or_trailing_spaces_is_rejected() {
        Pessoa pessoa = Pessoa.builder().contato(new Contato(" (12) 3456-7890 ")).build();
        IncluirPessoaRegras regras = new IncluirPessoaRegras(null);
        regras.verificarFormatoTelefone(pessoa);
    }

    @Test
    public void test_valid_contact_not_already_registered_should_pass_without_exceptions() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        assertDoesNotThrow(() -> regras.validarContatoJaCadastrado(pessoa));
    }

    @Test
    public void test_contact_already_registered_should_throw_exception() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarContatoJaCadastrado(pessoa));
        assertEquals("Contato já cadastrado", exception.getMessage());
    }

    @Test
    public void test_method_should_call_service_contatoJaUtilizado_exactly_once() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        regras.validarContatoJaCadastrado(pessoa);

        verify(service, times(1)).contatoJaUtilizado(pessoa);
    }

    @Test
    public void test_method_should_handle_valid_pessoa_object_with_all_required_fields_correctly() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        assertDoesNotThrow(() -> regras.validarContatoJaCadastrado(pessoa));
    }

    @Test
    public void test_method_should_allow_further_processing_if_contact_is_not_already_registered() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        assertDoesNotThrow(() -> regras.validarContatoJaCadastrado(pessoa));
    }

    @Test
    public void test_service_method_contatoJaUtilizado_returning_false_should_be_handled_gracefully() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        assertDoesNotThrow(() -> regras.validarContatoJaCadastrado(pessoa));
    }

    @Test
    public void test_service_method_contatoJaUtilizado_returning_true_should_be_handled_gracefully() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, new Contato("(12) 34567-8910"));

        when(service.contatoJaUtilizado(pessoa)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> regras.validarContatoJaCadastrado(pessoa));
        assertEquals("Contato já cadastrado", exception.getMessage());
    }

    @Test
    public void test_method_should_not_modify_the_pessoa_object() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Contato contatoOriginal = new Contato("(12) 34567-8910");
        Pessoa pessoa = new Pessoa(1L, "Valid Name", null, contatoOriginal);

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        regras.validarContatoJaCadastrado(pessoa);

        assertEquals(contatoOriginal, pessoa.getContato());
    }

    @Test
    public void test_method_should_not_have_side_effects_on_other_fields_of_pessoa() {
        PessoaService service = mock(PessoaService.class);
        IncluirPessoaRegras regras = new IncluirPessoaRegras(service);
        Endereco enderecoOriginal = new Endereco(1L, "Bairro");
        Contato contatoOriginal = new Contato("(12) 34567-8910");
        Pessoa pessoa = new Pessoa(1L, "Valid Name", enderecoOriginal, contatoOriginal);

        when(service.contatoJaUtilizado(pessoa)).thenReturn(false);

        regras.validarContatoJaCadastrado(pessoa);

        assertEquals(enderecoOriginal, pessoa.getEndereco());
        assertEquals(contatoOriginal, pessoa.getContato());
    }
}