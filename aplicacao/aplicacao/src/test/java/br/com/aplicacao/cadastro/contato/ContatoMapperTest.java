package br.com.aplicacao.cadastro.contato;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContatoMapperTest {

    @Test
    public void test_converts_to_domain_valid_id_and_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone("123456789").build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals("123456789", contato.getTelefone());
    }

    @Test
    public void test_to_domain_handles_null_id_and_valid_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(null).telefone("123456789").build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertNull(contato.getId());
        assertEquals("123456789", contato.getTelefone());
    }

    @Test
    public void test_to_domain_handles_valid_id_and_null_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone(null).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertNull(contato.getTelefone());
    }

    @Test
    public void test_converts_both_id_and_telefone_as_null() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(null).telefone(null).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertNull(contato.getId());
        assertNull(contato.getTelefone());
    }

    @Test
    public void test_converts_non_null_id_and_empty_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone("").build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals("", contato.getTelefone());
    }

    @Test
    public void test_handles_null_contatodata_input() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = mapper.toDomain((ContatoData) null);
        assertNull(contato);
    }

    @Test
    public void test_converts_special_characters_in_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone("!@#$%^&*()").build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals("!@#$%^&*()", contato.getTelefone());
    }

    @Test
    public void test_converts_maximum_length_telefone_string() {
        String maxLengthTelefone = "12345678901234567890";
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone(maxLengthTelefone).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals(maxLengthTelefone, contato.getTelefone());
    }

    @Test
    public void test_converts_minimum_length_telefone_string() {
        String minLengthTelefone = "1";
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone(minLengthTelefone).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals(minLengthTelefone, contato.getTelefone());
    }

    @Test
    public void test_converts_whitespace_only_telefone_string() {
        String whitespaceTelefone = "   ";
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone(whitespaceTelefone).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals(whitespaceTelefone, contato.getTelefone());
    }

    @Test
    public void test_handles_negative_id_correctly() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(-1L).telefone("123456789").build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(-1L, contato.getId().longValue());
        assertEquals("123456789", contato.getTelefone());
    }

    @Test
    public void test_converts_leading_trailing_spaces_in_telefone() {
        String telefoneWithSpaces = " 123456789 ";
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = ContatoData.builder().id(1L).telefone(telefoneWithSpaces).build();
        Contato contato = mapper.toDomain(contatoData);
        assertNotNull(contato);
        assertEquals(1L, contato.getId().longValue());
        assertEquals(telefoneWithSpaces, contato.getTelefone());
    }

    @Test
    public void test_converts_to_data_valid_id_and_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(1L).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(1L, contatoData.getId().longValue());
        assertEquals("123456789", contatoData.getTelefone());
    }

    @Test
    public void test_to_data_handles_null_id_and_valid_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(null).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertNull(contatoData.getId());
        assertEquals("123456789", contatoData.getTelefone());
    }

    @Test
    public void test_to_data_handles_valid_id_and_null_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(1L).telefone(null).build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(1L, contatoData.getId().longValue());
        assertNull(contatoData.getTelefone());
    }

    @Test
    public void test_handles_both_id_and_telefone_as_null() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(null).telefone(null).build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertNull(contatoData.getId());
        assertNull(contatoData.getTelefone());
    }

    @Test
    public void test_returns_matching_id_and_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(1L).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(contato.getId(), contatoData.getId());
        assertEquals(contato.getTelefone(), contatoData.getTelefone());
    }

    @Test
    public void test_contato_object_is_null() {
        ContatoMapper mapper = new ContatoMapper();
        ContatoData contatoData = mapper.toData((Contato) null);
        assertNull(contatoData);
    }

    @Test
    public void test_contato_has_empty_telefone_string() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(1L).telefone("").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(1L, contatoData.getId().longValue());
        assertEquals("", contatoData.getTelefone());
    }

    @Test
    public void test_contato_has_special_characters_in_telefone() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(1L).telefone("!@#$%^&*()").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(1L, contatoData.getId().longValue());
        assertEquals("!@#$%^&*()", contatoData.getTelefone());
    }

    @Test
    public void test_contato_has_very_long_telefone_string() {
        ContatoMapper mapper = new ContatoMapper();
        String longTelefone = "1".repeat(1000);
        Contato contato = Contato.builder().id(1L).telefone(longTelefone).build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(1L, contatoData.getId().longValue());
        assertEquals(longTelefone, contatoData.getTelefone());
    }

    @Test
    public void test_contato_has_negative_id() {
        ContatoMapper mapper = new ContatoMapper();
        Contato contato = Contato.builder().id(-1L).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(-1L, contatoData.getId().longValue());
        assertEquals("123456789", contatoData.getTelefone());
    }

    @Test
    public void test_contato_maximum_possible_id_value() {
        ContatoMapper mapper = new ContatoMapper();
        Long maxId = Long.MAX_VALUE;
        Contato contato = Contato.builder().id(maxId).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(maxId, contatoData.getId());
        assertEquals("123456789", contatoData.getTelefone());
    }

    @Test
    public void test_contato_minimum_possible_id_value() {
        ContatoMapper mapper = new ContatoMapper();
        Long minId = Long.MIN_VALUE;
        Contato contato = Contato.builder().id(minId).telefone("123456789").build();
        ContatoData contatoData = mapper.toData(contato);
        assertNotNull(contatoData);
        assertEquals(minId, contatoData.getId());
        assertEquals("123456789", contatoData.getTelefone());
    }
}
