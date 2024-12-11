package br.com.aplicacao.cadastro.pessoa;

import br.com.aplicacao.cadastro.contato.ContatoMapper;
import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import br.com.aplicacao.cadastro.endereco.EnderecoMapper;
import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import br.com.aplicacao.cadastro.endereco.infra.data.EnderecoData;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PessoaMapperTest {

    @Test
    public void test_toDomain_converts_PessoaData_with_all_fields_populated_to_Pessoa_correctly() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        EnderecoData enderecoData = new EnderecoData(1L, "Bairro");
        ContatoData contatoData = new ContatoData(1L, "123456789");
        PessoaData pessoaData = new PessoaData(1L, "John Doe", enderecoData, contatoData);

        Endereco endereco = new Endereco(1L, "Bairro");
        Contato contato = new Contato(1L, "123456789");

        when(enderecoMapper.toDomain(enderecoData)).thenReturn(endereco);
        when(contatoMapper.toDomain(contatoData)).thenReturn(contato);

        Pessoa result = pessoaMapper.toDomain(pessoaData);

        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getNome());
        assertEquals(endereco, result.getEndereco());
        assertEquals(contato, result.getContato());
    }

    @Test
    public void test_toData_converts_Pessoa_with_all_fields_populated_to_PessoaData_correctly() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        Endereco endereco = new Endereco(1L, "Bairro");
        Contato contato = new Contato(1L, "123456789");
        Pessoa pessoa = new Pessoa(1L, "John Doe", endereco, contato);

        EnderecoData enderecoData = new EnderecoData(1L, "Bairro");
        ContatoData contatoData = new ContatoData(1L, "123456789");

        when(enderecoMapper.toData(endereco)).thenReturn(enderecoData);
        when(contatoMapper.toData(contato)).thenReturn(contatoData);

        PessoaData result = pessoaMapper.toData(pessoa);

        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getNome());
        assertEquals(enderecoData, result.getEndereco());
        assertEquals(contatoData, result.getContato());
    }

    @Test
    public void test_toDomain_converts_PessoaData_with_nested_EnderecoData_and_ContatoData_correctly() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        EnderecoData enderecoData = new EnderecoData(1L, "Bairro");
        ContatoData contatoData = new ContatoData(1L, "123456789");
        PessoaData pessoaData = new PessoaData(1L, "John Doe", enderecoData, contatoData);

        Endereco endereco = new Endereco(1L, "Bairro");
        Contato contato = new Contato(1L, "123456789");

        when(enderecoMapper.toDomain(enderecoData)).thenReturn(endereco);
        when(contatoMapper.toDomain(contatoData)).thenReturn(contato);

        Pessoa result = pessoaMapper.toDomain(pessoaData);

        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getNome());
        assertEquals(endereco, result.getEndereco());
        assertEquals(contato, result.getContato());
    }

    @Test
    public void test_toData_converts_Pessoa_with_nested_Endereco_and_Contato_correctly() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        Endereco endereco = new Endereco(1L, "Bairro");
        Contato contato = new Contato(1L, "123456789");
        Pessoa pessoa = new Pessoa(1L, "John Doe", endereco, contato);

        EnderecoData enderecoData = new EnderecoData(1L, "Bairro");
        ContatoData contatoData = new ContatoData(1L, "123456789");

        when(enderecoMapper.toData(endereco)).thenReturn(enderecoData);
        when(contatoMapper.toData(contato)).thenReturn(contatoData);

        PessoaData result = pessoaMapper.toData(pessoa);

        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getNome());
        assertEquals(enderecoData, result.getEndereco());
        assertEquals(contatoData, result.getContato());
    }

    @Test
    public void test_toDomain_handles_null_PessoaData_input_gracefully() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        Pessoa result = pessoaMapper.toDomain((PessoaData) null);

        assertNull(result);
    }

    @Test
    public void test_toData_handles_null_Pessoa_input_gracefully() {
        ContatoMapper contatoMapper = mock(ContatoMapper.class);
        EnderecoMapper enderecoMapper = mock(EnderecoMapper.class);
        PessoaMapper pessoaMapper = new PessoaMapper(contatoMapper, enderecoMapper);

        PessoaData result = pessoaMapper.toData((Pessoa) null);

        assertNull(result);
    }
}