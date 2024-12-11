package br.com.aplicacao.cadastro.endereco.dto;

import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EnderecoDtoTest {

    @Test
    public void test_converts_valid_id_and_bairro() {
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_null_id() {
        EnderecoDto dto = EnderecoDto.builder().id(null).bairro("Centro").build();
        Endereco entity = dto.toEntity();
        assertNull(entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_null_bairro() {
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro(null).build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertNull(entity.getBairro());
    }

    @Test
    public void test_converts_both_null_fields() {
        EnderecoDto dto = EnderecoDto.builder().id(null).bairro(null).build();
        Endereco entity = dto.toEntity();
        assertNull(entity.getId());
        assertNull(entity.getBairro());
    }

    @Test
    public void test_converts_all_fields_populated() {
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_empty_string_bairro() {
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro("").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_special_characters_bairro() {
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro("@#%&*").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_large_id_value() {
        Long largeId = Long.MAX_VALUE;
        EnderecoDto dto = EnderecoDto.builder().id(largeId).bairro("Centro").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_large_string_bairro() {
        String largeString = "a".repeat(1000);
        EnderecoDto dto = EnderecoDto.builder().id(1L).bairro(largeString).build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_converts_negative_id_value() {
        Long negativeId = -1L;
        EnderecoDto dto = EnderecoDto.builder().id(negativeId).bairro("Centro").build();
        Endereco entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBairro(), entity.getBairro());
    }

    @Test
    public void test_entity_is_new_instance() {
        EnderecoDto dto = new EnderecoDto(1L, "Centro");
        Endereco entity = dto.toEntity();
        assertNotSame(dto, entity);
    }

    @Test
    public void test_immutability_of_entity_after_conversion() {
        EnderecoDto dto = new EnderecoDto(1L, "Centro");
        Endereco entity = dto.toEntity();

        dto.setId(2L);
        dto.setBairro("Updated Bairro");

        assertNotEquals(dto.getId(), entity.getId());
        assertNotEquals(dto.getBairro(), entity.getBairro());

        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("Centro", entity.getBairro());
    }
}
