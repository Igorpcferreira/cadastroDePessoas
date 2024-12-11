package br.com.aplicacao.cadastro.pessoa.dto;

import br.com.aplicacao.cadastro.contato.dto.ContatoDto;
import br.com.aplicacao.cadastro.endereco.dto.EnderecoDto;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import org.junit.Test;

import static org.junit.Assert.*;

public class PessoaDtoTest {

    @Test
    public void test_converts_pessoa_dto_with_all_fields_populated_to_pessoa_entity_correctly() {
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("John Doe").contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoaDto.getId(), pessoa.getId());
        assertEquals(pessoaDto.getNome(), pessoa.getNome());
        assertEquals(pessoaDto.getContato().getId(), pessoa.getContato().getId());
        assertEquals(pessoaDto.getEndereco().getId(), pessoa.getEndereco().getId());
    }

    @Test
    public void test_converts_pessoa_dto_with_null_id_to_pessoa_entity_with_null_id() {
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().nome("John Doe").contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertNull(pessoa.getId());
        assertEquals(pessoaDto.getNome(), pessoa.getNome());
        assertEquals(pessoaDto.getContato().getId(), pessoa.getContato().getId());
        assertEquals(pessoaDto.getEndereco().getId(), pessoa.getEndereco().getId());
    }

    @Test
    public void test_converts_pessoa_dto_with_null_nome_to_pessoa_entity_with_null_nome() {
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoaDto.getId(), pessoa.getId());
        assertNull(pessoa.getNome());
        assertEquals(pessoaDto.getContato().getId(), pessoa.getContato().getId());
        assertEquals(pessoaDto.getEndereco().getId(), pessoa.getEndereco().getId());
    }

    @Test
    public void test_converts_pessoa_dto_with_empty_strings_for_nome_to_pessoa_entity_with_empty_strings_for_nome() {
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("").contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoaDto.getId(), pessoa.getId());
        assertEquals("", pessoa.getNome());
        assertEquals(pessoaDto.getContato().getId(), pessoa.getContato().getId());
        assertEquals(pessoaDto.getEndereco().getId(), pessoa.getEndereco().getId());
    }

    @Test
    public void test_converts_pessoa_dto_with_partially_populated_contato_dto_to_pessoa_entity_with_corresponding_contato_fields() {
        ContatoDto contatoDto = ContatoDto.builder().telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("John Doe").contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoaDto.getId(), pessoa.getId());
        assertEquals(pessoaDto.getNome(), pessoa.getNome());
        assertNull(pessoa.getContato().getId());
        assertEquals(pessoa.getContato().getTelefone(), contatoDto.getTelefone());
        assertEquals(pessoa.getEndereco().getId(), enderecoDto.getId());
    }

    @Test
    public void test_converts_pessoa_dto_with_partially_populated_endereco_dto_to_pessoa_entity_with_corresponding_endereco_fields() {
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("John Doe").contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoaDto.getId(), pessoa.getId());
        assertEquals(pessoaDto.getNome(), pessoa.getNome());
        assertEquals(contatoDto.getId(), pessoa.getContato().getId());
        assertNull(pessoa.getEndereco().getId());
        assertEquals(enderecoDto.getBairro(), pessoa.getEndereco().getBairro());
    }

    @Test
    public void test_handles_pessoa_dto_with_exceptionally_large_strings_for_nome() {
        String largeString = "a".repeat(10000);
        ContatoDto contatoDto = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto enderecoDto = EnderecoDto.builder().id(1L).bairro("Centro").build();
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome(largeString).contato(contatoDto).endereco(enderecoDto).build();

        Pessoa pessoa = pessoaDto.toEntity();

        assertEquals(pessoa.getNome(), largeString);
    }

    @Test
    public void test_ensures_immutability_of_pessoa_entity_after_conversion() {
        ContatoDto contato = ContatoDto.builder().id(1L).telefone("123456789").build();
        EnderecoDto endereco = EnderecoDto.builder().id(1L).bairro("Centro").build();
        Pessoa originalPessoa = new Pessoa(1L, "John Doe", endereco.toEntity(), contato.toEntity());
        PessoaDto pessoaDto = PessoaDto.builder().id(1L).nome("Jane Doe").contato(contato).endereco(endereco).build();
        Pessoa pessoa = pessoaDto.toEntity();
        assertNotEquals(originalPessoa, pessoa);
    }
}