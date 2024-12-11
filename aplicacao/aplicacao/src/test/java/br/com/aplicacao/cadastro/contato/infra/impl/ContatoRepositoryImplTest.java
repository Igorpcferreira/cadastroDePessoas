package br.com.aplicacao.cadastro.contato.infra.impl;

import br.com.aplicacao.cadastro.contato.ContatoMapper;
import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.ContatoDataRepository;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import jakarta.transaction.Transactional;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContatoRepositoryImplTest {

    @Test
    public void test_save_new_contato() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Contato contato = new Contato("123456789");
        ContatoData contatoData = new ContatoData(null, "123456789");

        when(contatoMapper.toData(contato)).thenReturn(contatoData);
        when(contatoDataRepository.save(contatoData)).thenReturn(contatoData);
        when(contatoMapper.toDomain(contatoData)).thenReturn(contato);

        Contato savedContato = contatoRepository.incluir(contato);

        assertNotNull(savedContato);
        assertEquals("123456789", savedContato.getTelefone());
    }

    @Test
    public void test_update_existing_contato() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Contato contato = new Contato(1L, "987654321");
        ContatoData contatoData = new ContatoData(1L, "987654321");

        when(contatoMapper.toData(contato)).thenReturn(contatoData);
        when(contatoDataRepository.save(contatoData)).thenReturn(contatoData);
        when(contatoMapper.toDomain(contatoData)).thenReturn(contato);

        Contato updatedContato = contatoRepository.alterar(contato);

        assertNotNull(updatedContato);
        assertEquals("987654321", updatedContato.getTelefone());
    }

    @Test
    public void test_delete_contato_by_id() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Long id = 1L;

        doNothing().when(contatoDataRepository).deleteById(id);

        Long deletedId = contatoRepository.excluir(id);

        assertEquals(id, deletedId);
    }

    @Test
    public void test_retrieve_contato_by_id() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Long id = 1L;
        ContatoData contatoData = new ContatoData(id, "123456789");
        Contato contato = new Contato(id, "123456789");

        when(contatoDataRepository.findById(id)).thenReturn(Optional.of(contatoData));
        when(contatoMapper.toDomain(contatoData)).thenReturn(contato);

        Optional<Contato> retrievedContato = Optional.ofNullable(contatoRepository.getMapper().toDomain(contatoData));

        assertTrue(retrievedContato.isPresent());
        assertEquals("123456789", retrievedContato.get().getTelefone());
    }

    @Test
    public void test_mapper_conversion() {
        ContatoMapper contatoMapper = new ContatoMapper();

        Contato contato = new Contato(1L, "123456789");
        ContatoData contatoData = new ContatoData(1L, "123456789");

        assertEquals(contato.getId(), contatoMapper.toDomain(contatoMapper.toData(contato)).getId());
        assertEquals(contato.getTelefone(), contatoMapper.toDomain(contatoMapper.toData(contato)).getTelefone());
        assertEquals(contatoData.getId(), contatoMapper.toData(contatoMapper.toDomain(contatoData)).getId());
        assertEquals(contatoData.getTelefone(), contatoMapper.toData(contatoMapper.toDomain(contatoData)).getTelefone());
    }

    @Test
    @Transactional
    public void test_transactional_behaviour() {
    }

    @Test(expected = RuntimeException.class)
    public void test_save_contato_with_null_fields() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Contato contato = new Contato(null);

        when(contatoMapper.toData(contato)).thenThrow(new RuntimeException("Null fields"));

        contatoRepository.incluir(contato);
    }

    @Test(expected = RuntimeException.class)
    public void test_update_non_existent_contato() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        ContatoDataRepository contatoDataRepository = mock(ContatoDataRepository.class);
        ContatoRepositoryImpl contatoRepository = new ContatoRepositoryImpl(contatoMapper, contatoDataRepository);

        Contato contato = new Contato(999L, "987654321");

        when(contatoMapper.toData(contato)).thenReturn(new ContatoData(999L, "987654321"));
        when(contatoDataRepository.save(any())).thenThrow(new RuntimeException("Non-existent entity"));

        contatoRepository.alterar(contato);
    }
}