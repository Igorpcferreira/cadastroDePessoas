package br.com.aplicacao.cadastro.pessoa.infra.impl;

import br.com.aplicacao.cadastro.contato.ContatoMapper;
import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import br.com.aplicacao.cadastro.endereco.EnderecoMapper;
import br.com.aplicacao.cadastro.pessoa.PessoaMapper;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.infra.PessoaDataRepository;
import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PessoaRepositoryImplTest {

    @Test
    public void test_consultar_valid_id() {
        var pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        PessoaData pessoaData = new PessoaData(1L, "John Doe", null, null);
        Pessoa pessoa = new Pessoa(1L, "John Doe", null, null);

        when(pessoaDataRepository.findById(1L)).thenReturn(Optional.of(pessoaData));
        when(pessoaMapper.toDomain(pessoaData)).thenReturn(pessoa);

        Optional<Pessoa> result = pessoaRepository.consultar(1L);

        assertEquals(true, result.isPresent());
        assertEquals(pessoa, result.get());
    }

    @Test
    public void test_contato_ja_utilizado_true() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Contato contato = new Contato();
        contato.setTelefone("123456789");
        Pessoa pessoa = new Pessoa(1L, "John Doe", null, contato);

        when(pessoaMapper.toData(pessoa)).thenReturn(new PessoaData(1L, "John Doe", null, new ContatoData(1L, "123456789")));
        when(pessoaDataRepository.telefoneJaCadastrado("123456789")).thenReturn(true);

        boolean result = pessoaRepository.contatoJaUtilizado(pessoa);

        assertTrue(result);
    }

    @Test
    public void test_incluir() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = new Pessoa(null, "John Doe", null, null);
        PessoaData pessoaData = new PessoaData(null, "John Doe", null, null);
        PessoaData savedPessoaData = new PessoaData(1L, "John Doe", null, null);
        Pessoa savedPessoa = new Pessoa(1L, "John Doe", null, null);

        when(pessoaMapper.toData(pessoa)).thenReturn(pessoaData);
        when(pessoaDataRepository.save(pessoaData)).thenReturn(savedPessoaData);
        when(pessoaMapper.toDomain(savedPessoaData)).thenReturn(savedPessoa);

        Pessoa result = pessoaRepository.incluir(pessoa);

        assertEquals(savedPessoa, result);
    }

    @Test
    public void test_alterar() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = new Pessoa(1L, "John Doe", null, null);
        PessoaData pessoaData = new PessoaData(1L, "John Doe", null, null);
        Pessoa updatedPessoa = new Pessoa(1L, "John Doe Updated", null, null);
        PessoaData updatedPessoaData = new PessoaData(1L, "John Doe Updated", null, null);

        when(pessoaMapper.toData(pessoa)).thenReturn(pessoaData);
        when(pessoaDataRepository.save(pessoaData)).thenReturn(updatedPessoaData);
        when(pessoaMapper.toDomain(updatedPessoaData)).thenReturn(updatedPessoa);

        Pessoa result = pessoaRepository.alterar(pessoa);

        assertEquals(updatedPessoa, result);
    }

    @Test
    public void test_consultar_invalid_id() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        when(pessoaDataRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Pessoa> result = pessoaRepository.consultar(999L);

        assertFalse(result.isPresent());
    }

    @Test
    public void test_contato_ja_utilizado_false() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Contato contato = new Contato();
        contato.setTelefone("123456789");
        Pessoa pessoa = new Pessoa(1L, "John Doe", null, contato);

        when(pessoaMapper.toData(pessoa)).thenReturn(new PessoaData(1L, "John Doe", null, new ContatoData(1L, "123456789")));
        when(pessoaDataRepository.telefoneJaCadastrado("123456789")).thenReturn(false);

        boolean result = pessoaRepository.contatoJaUtilizado(pessoa);

        assertFalse(result);
    }

    @Test
    public void test_returns_page_of_pessoa_objects_when_multiple_records() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        List<PessoaData> pessoaDataList = Arrays.asList(new PessoaData(), new PessoaData());
        Page<PessoaData> pessoaDataPage = new PageImpl<>(pessoaDataList);
        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenReturn(pessoaDataPage);

        Page<Pessoa> result = pessoaRepository.listar();

        assertEquals(2, result.getTotalElements());
        verify(pessoaMapper, times(2)).toDomain(any(PessoaData.class));
    }

    @Test
    public void test_returns_empty_page_when_no_records() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Page<PessoaData> pessoaDataPage = new PageImpl<>(Collections.emptyList());
        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenReturn(pessoaDataPage);

        Page<Pessoa> result = pessoaRepository.listar();

        assertTrue(result.isEmpty());
        verify(pessoaMapper, never()).toDomain(any(PessoaData.class));
    }

    @Test
    public void test_maps_pessoadata_to_pessoa_correctly_for_all_records() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        PessoaData pessoaData1 = new PessoaData();
        PessoaData pessoaData2 = new PessoaData();
        List<PessoaData> pessoaDataList = Arrays.asList(pessoaData1, pessoaData2);
        Page<PessoaData> pessoaDataPage = new PageImpl<>(pessoaDataList);
        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenReturn(pessoaDataPage);

        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        when(pessoaMapper.toDomain(pessoaData1)).thenReturn(pessoa1);
        when(pessoaMapper.toDomain(pessoaData2)).thenReturn(pessoa2);

        Page<Pessoa> result = pessoaRepository.listar();

        assertEquals(Arrays.asList(pessoa1, pessoa2), result.getContent());
    }

    @Test
    public void test_retrieves_all_records_without_pagination_constraints() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        List<PessoaData> pessoaDataList = Arrays.asList(new PessoaData(), new PessoaData());
        Page<PessoaData> pessoaDataPage = new PageImpl<>(pessoaDataList);
        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenReturn(pessoaDataPage);

        Page<Pessoa> result = pessoaRepository.listar();

        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void test_handles_large_datasets_efficiently() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        List<PessoaData> largePessoaDataList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largePessoaDataList.add(new PessoaData());
        }
        Page<PessoaData> largePessoaDataPage = new PageImpl<>(largePessoaDataList);
        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenReturn(largePessoaDataPage);

        Page<Pessoa> result = pessoaRepository.listar();

        assertEquals(1000, result.getTotalElements());
    }

    @Test
    public void test_returns_empty_page_if_database_connection_is_lost() {
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaMapper pessoaMapper = mock(PessoaMapper.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        when(pessoaDataRepository.findAll(Pageable.unpaged())).thenThrow(new RuntimeException("Database connection lost"));

        Page<Pessoa> result;
        try {
            result = pessoaRepository.listar();
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Database connection lost", e.getMessage());
            result = Page.empty();
            assertTrue(result.isEmpty());
        }
    }

    @Test
    public void returns_true_when_phone_number_used_by_another_person() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("123456789")).build();
        when(pessoaDataRepository.telefoneJaCadastradoSemSerEu("123456789", 1L)).thenReturn(true);

        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertTrue(result);
    }

    @Test
    public void returns_false_when_phone_number_not_used_by_any_other_person() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("123456789")).build();
        when(pessoaDataRepository.telefoneJaCadastradoSemSerEu("123456789", 1L)).thenReturn(false);

        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }

    @Test
    public void correctly_maps_pessoa_to_pessoadata_before_querying_repository() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("123456789")).build();
        pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        verify(pessoaDataRepository).telefoneJaCadastradoSemSerEu("123456789", 1L);
    }

    @Test
    public void uses_correct_phone_number_and_id_in_repository_query() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("123456789")).build();
        pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        verify(pessoaDataRepository).telefoneJaCadastradoSemSerEu("123456789", 1L);
    }

    @Test
    public void handles_null_phone_number_in_pessoa_object_gracefully() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato(null)).build();
        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }

    @Test
    public void handles_null_id_in_pessoa_object_gracefully() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(null).contato(new Contato("123456789")).build();
        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }

    @Test
    public void handles_empty_phone_number_string_in_pessoa_object() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("")).build();
        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }

    @Test
    public void handles_non_existent_phone_number_in_repository() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        when(pessoaDataRepository.telefoneJaCadastradoSemSerEu("nonexistent", 1L)).thenReturn(false);

        Pessoa pessoa = Pessoa.builder().id(1L).contato(new Contato("nonexistent")).build();
        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }

    @Test
    public void handles_non_existent_id_in_repository() {
        PessoaMapper pessoaMapper = new PessoaMapper(new ContatoMapper(), new EnderecoMapper());
        PessoaDataRepository pessoaDataRepository = mock(PessoaDataRepository.class);
        PessoaRepositoryImpl pessoaRepository = new PessoaRepositoryImpl(pessoaMapper, pessoaDataRepository);

        when(pessoaDataRepository.telefoneJaCadastradoSemSerEu("123456789", 999L)).thenReturn(false);

        Pessoa pessoa = Pessoa.builder().id(999L).contato(new Contato("123456789")).build();
        boolean result = pessoaRepository.contatoJaUtilizadoSemSerEu(pessoa);

        assertFalse(result);
    }
}
