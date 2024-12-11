package br.com.aplicacao.cadastro.contato.dto;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ContatoDtoTest {

    @Test
    public void test_converts_valid_id_and_telefone() {
        ContatoDto dto = ContatoDto.builder().id(1L).telefone("123456789").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_null_id() {
        ContatoDto dto = ContatoDto.builder().id(null).telefone("123456789").build();
        Contato entity = dto.toEntity();
        assertNull(entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_null_telefone() {
        ContatoDto dto = ContatoDto.builder().id(1L).telefone(null).build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertNull(entity.getTelefone());
    }

    @Test
    public void test_converts_both_null() {
        ContatoDto dto = ContatoDto.builder().id(null).telefone(null).build();
        Contato entity = dto.toEntity();
        assertNull(entity.getId());
        assertNull(entity.getTelefone());
    }

    @Test
    public void test_converts_non_null_id_and_telefone() {
        ContatoDto dto = ContatoDto.builder().id(1L).telefone("123456789").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_empty_telefone() {
        ContatoDto dto = ContatoDto.builder().id(1L).telefone("").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_special_characters_in_telefone() {
        ContatoDto dto = ContatoDto.builder().id(1L).telefone("!@#$%^&*()").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_max_length_telefone() {
        String maxLengthTelefone = "12345678901234567890"; // Assuming max length is 20 characters
        ContatoDto dto = ContatoDto.builder().id(1L).telefone(maxLengthTelefone).build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_min_length_telefone() {
        String minLengthTelefone = "1"; // Assuming min length is 1 character
        ContatoDto dto = ContatoDto.builder().id(1L).telefone(minLengthTelefone).build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_negative_id() {
        ContatoDto dto = ContatoDto.builder().id(-1L).telefone("123456789").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_leading_trailing_spaces_in_telefone() {
        String telefoneWithSpaces = " 123456789 ";
        ContatoDto dto = ContatoDto.builder().id(1L).telefone(telefoneWithSpaces).build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }

    @Test
    public void test_converts_zero_id() {
        ContatoDto dto = ContatoDto.builder().id(0L).telefone("123456789").build();
        Contato entity = dto.toEntity();
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTelefone(), entity.getTelefone());
    }
}