package br.com.aplicacao.cadastro.endereco;

import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import br.com.aplicacao.cadastro.endereco.infra.data.EnderecoData;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnderecoMapperTest {

    @Test
    public void test_converts_valid_enderecoData_to_endereco_correctly() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertNotNull(endereco);
        assertEquals(enderecoData.getId(), endereco.getId());
        assertEquals(enderecoData.getBairro(), endereco.getBairro());
    }

    @Test
    public void test_maps_id_field_from_enderecoData_to_endereco() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(enderecoData.getId(), endereco.getId());
    }

    @Test
    public void test_maps_bairro_field_from_enderecoData_to_endereco() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(enderecoData.getBairro(), endereco.getBairro());
    }

    @Test
    public void test_returns_fully_populated_endereco_when_enderecoData_is_fully_populated() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertNotNull(endereco);
        assertEquals(1L, (long) endereco.getId());
        assertEquals("Centro", endereco.getBairro());
    }

    @Test
    public void test_returns_null_when_enderecoData_is_null() {
        EnderecoMapper mapper = new EnderecoMapper();
        Endereco endereco = mapper.toDomain((EnderecoData) null);
        assertNull(endereco);
    }

    @Test
    public void test_handles_enderecoData_with_null_id_gracefully() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(null).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertNull(endereco.getId());
        assertEquals("Centro", endereco.getBairro());
    }

    @Test
    public void test_handles_enderecoData_with_null_bairro_gracefully() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro(null).build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(1L, (long) endereco.getId());
        assertNull(endereco.getBairro());
    }

    @Test
    public void test_handles_enderecoData_with_empty_bairro_string() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(1L, (long) endereco.getId());
        assertEquals("", endereco.getBairro());
    }

    @Test
    public void test_handles_enderecoData_with_special_characters_in_bairro() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("@#%&*").build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(1L, (long) endereco.getId());
        assertEquals("@#%&*", endereco.getBairro());
    }

    @Test
    public void test_handles_enderecoData_with_maximum_length_strings() {
        String maxLengthString = "a".repeat(255);
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro(maxLengthString).build();
        Endereco endereco = mapper.toDomain(enderecoData);
        assertEquals(1L, (long) endereco.getId());
        assertEquals(maxLengthString, endereco.getBairro());
    }

    @Test
    public void test_ensures_immutability_of_endereco_object_after_conversion() {
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = EnderecoData.builder().id(1L).bairro("Centro").build();
        Endereco endereco = mapper.toDomain(enderecoData);

        enderecoData.setBairro("Changed");

        assertEquals("Centro", endereco.getBairro());
    }

    @Test
    public void test_verifies_builder_pattern_is_used_correctly_in_endereco() {
        Long id = 1L;
        String bairro = "Centro";

        Endereco endereco = Endereco.builder()
                .id(id)
                .bairro(bairro)
                .build();

        assertNotNull(endereco);
        assertEquals(id, endereco.getId());
        assertEquals(bairro, endereco.getBairro());
    }

    @Test
    public void converts_valid_endereco_to_endereco_data_correctly() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertNotNull(enderecoData);
        assertEquals(endereco.getId(), enderecoData.getId());
        assertEquals(endereco.getBairro(), enderecoData.getBairro());
    }

    @Test
    public void preserves_id_field_from_endereco_to_endereco_data() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertEquals(endereco.getId(), enderecoData.getId());
    }

    @Test
    public void preserves_bairro_field_from_endereco_to_endereco_data() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertEquals(endereco.getBairro(), enderecoData.getBairro());
    }

    @Test
    public void returns_non_null_enderecodata_when_given_non_null_endereco() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertNotNull(enderecoData);
    }

    @Test
    public void returns_null_when_given_null_endereco() {
        EnderecoMapper mapper = new EnderecoMapper();
        assertNull(mapper.toData((Endereco) null));
    }

    @Test
    public void handles_endereco_with_null_id_field() {
        Endereco endereco = Endereco.builder().id(null).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertNull(enderecoData.getId());
        assertEquals(endereco.getBairro(), enderecoData.getBairro());
    }

    @Test
    public void handles_endereco_with_null_bairro_field() {
        Endereco endereco = Endereco.builder().id(1L).bairro(null).build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertEquals(endereco.getId(), enderecoData.getId());
        assertNull(enderecoData.getBairro());
    }

    @Test
    public void handles_endereco_with_both_id_and_bairro_fields_as_null() {
        Endereco endereco = Endereco.builder().id(null).bairro(null).build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertNull(enderecoData.getId());
        assertNull(enderecoData.getBairro());
    }

    @Test
    public void ensures_no_additional_fields_set_in_enderecodata() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);
        assertEquals(1L, (long) enderecoData.getId());
        assertEquals("Centro", enderecoData.getBairro());
    }

    @Test
    public void verifies_immutability_of_enderecodata_after_conversion() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);

        endereco.setId(2L);
        endereco.setBairro("Changed");

        assertEquals(1L, (long) enderecoData.getId());
        assertEquals("Centro", enderecoData.getBairro());
    }

    @Test
    public void checks_handling_of_large_id_values() {
        Long largeId = Long.MAX_VALUE;
        Endereco endereco = Endereco.builder().id(largeId).bairro("Centro").build();
        EnderecoMapper mapper = new EnderecoMapper();
        EnderecoData enderecoData = mapper.toData(endereco);

        assertEquals(largeId, enderecoData.getId());
        assertEquals("Centro", enderecoData.getBairro());
    }
}