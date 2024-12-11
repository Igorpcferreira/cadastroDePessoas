package br.com.aplicacao.cadastro.pessoa.dto;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaListagemDtoTest {

    @Test
    public void test_converting_valid_pessoa_to_dto() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        Contato contato = Contato.builder().id(1L).telefone("123456789").build();
        Pessoa pessoa = Pessoa.builder().id(1L).nome("João").endereco(endereco).contato(contato).build();

        PessoaListagemDto dto = PessoaListagemDto.from(pessoa);

        assertNotNull(dto);
        assertEquals(pessoa.getId(), dto.getId());
        assertEquals(pessoa.getNome(), dto.getNome());
        assertEquals(pessoa.getEndereco().getBairro(), dto.getBairro());
        assertEquals(pessoa.getContato().getTelefone(), dto.getTelefone());
    }

    @Test
    public void test_converting_valid_dto_to_pessoa() {
        PessoaListagemDto dto = PessoaListagemDto.builder().id(1L).nome("João").bairro("Centro").telefone("123456789").build();

        Pessoa pessoa = dto.toEntity();

        assertNotNull(pessoa);
        assertEquals(dto.getId(), pessoa.getId());
        assertEquals(dto.getNome(), pessoa.getNome());
    }

    @Test
    public void test_all_fields_mapped_from_pessoa_to_dto() {
        Endereco endereco = Endereco.builder().id(1L).bairro("Centro").build();
        Contato contato = Contato.builder().id(1L).telefone("123456789").build();
        Pessoa pessoa = Pessoa.builder().id(1L).nome("João").endereco(endereco).contato(contato).build();

        PessoaListagemDto dto = PessoaListagemDto.from(pessoa);

        assertNotNull(dto);
        assertEquals(pessoa.getId(), dto.getId());
        assertEquals(pessoa.getNome(), dto.getNome());
        assertEquals(pessoa.getEndereco().getBairro(), dto.getBairro());
        assertEquals(pessoa.getContato().getTelefone(), dto.getTelefone());
    }

    @Test
    public void test_all_fields_mapped_from_dto_to_pessoa() {
        PessoaListagemDto dto = PessoaListagemDto.builder().id(1L).nome("João").bairro("Centro").telefone("123456789").build();

        Pessoa pessoa = dto.toEntity();

        assertNotNull(pessoa);
        assertEquals(dto.getId(), pessoa.getId());
        assertEquals(dto.getNome(), pessoa.getNome());
    }

    @Test
    public void test_handling_null_values_from_pessoa_to_dto() {
        Pessoa pessoa = null;

        PessoaListagemDto dto = PessoaListagemDto.from(pessoa);

        assertNull(dto);
    }

    @Test
    public void test_builder_pattern_for_dto() {
        PessoaListagemDto dto = PessoaListagemDto.builder()
                .id(1L)
                .nome("João")
                .bairro("Centro")
                .telefone("123456789")
                .build();

        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("João", dto.getNome());
        assertEquals("Centro", dto.getBairro());
        assertEquals("123456789", dto.getTelefone());
    }

    @Test
    public void test_converting_dto_with_null_fields_to_pessoa() {
        PessoaListagemDto dto = new PessoaListagemDto();

        Pessoa pessoa = dto.toEntity();

        assertNotNull(pessoa);
        assertNull(pessoa.getId());
        assertNull(pessoa.getNome());
    }

    @Test
    public void test_converting_pessoa_with_empty_strings_to_dto() {
        Endereco endereco = Endereco.builder().id(1L).bairro("").build();
        Contato contato = Contato.builder().id(1L).telefone("").build();
        Pessoa pessoa = new Pessoa(1L, "", endereco, contato);

        PessoaListagemDto dto = PessoaListagemDto.from(pessoa);

        assertNotNull(dto);
        assertEquals("", dto.getNome());
        assertEquals("", dto.getBairro());
        assertEquals("", dto.getTelefone());
    }

    @Test
    public void test_immutability_of_dto_after_creation() {
        Long id = 1L;
        String nome = "João";
        String bairro = "Centro";
        String telefone = "123456789";

        PessoaListagemDto dto = new PessoaListagemDto(id, nome, bairro, telefone);

        id = 2L;
        nome = "Maria";
        bairro = "Zona Sul";
        telefone = "987654321";

        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("João", dto.getNome());
        assertEquals("Centro", dto.getBairro());
        assertEquals("123456789", dto.getTelefone());
    }

}