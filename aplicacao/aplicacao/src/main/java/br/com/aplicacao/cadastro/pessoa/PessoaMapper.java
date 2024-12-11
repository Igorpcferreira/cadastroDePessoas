package br.com.aplicacao.cadastro.pessoa;

import br.com.aplicacao.cadastro.contato.ContatoMapper;
import br.com.aplicacao.cadastro.endereco.EnderecoMapper;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import br.com.aplicacao.core.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por realizar a conversão entre a entidade de domínio {@link Pessoa} e sua representação de dados
 * {@link PessoaData}. Implementa as operações de mapeamento definidas na classe {@link BaseMapper}.
 *
 * <p>Esta classe é anotada com {@link Component} para ser reconhecida e gerenciada automaticamente pelo Spring.</p>
 *
 * <p>Utiliza os mappers {@link ContatoMapper} e {@link EnderecoMapper} para realizar a conversão dos atributos
 * de contato e endereço da {@link Pessoa}.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Component
@RequiredArgsConstructor
public class PessoaMapper extends BaseMapper<Pessoa, PessoaData> {

    private final ContatoMapper contatoMapper;
    private final EnderecoMapper enderecoMapper;

    /**
     * Converte uma instância de {@link PessoaData} para {@link Pessoa}.
     *
     * @param pessoaData A entidade de dados {@link PessoaData} a ser convertida.
     * @return A entidade de domínio {@link Pessoa} correspondente.
     */
    @Override
    public Pessoa toDomain(PessoaData pessoaData) {
        if (pessoaData == null) return null;

        return Pessoa.builder()
                .id(pessoaData.getId())
                .nome(pessoaData.getNome())
                .endereco(enderecoMapper.toDomain(pessoaData.getEndereco()))
                .contato(contatoMapper.toDomain(pessoaData.getContato()))
                .build();
    }

    /**
     * Converte uma instância de {@link Pessoa} para {@link PessoaData}.
     *
     * @param pessoa A entidade de domínio {@link Pessoa} a ser convertida.
     * @return A entidade de dados {@link PessoaData} correspondente.
     */
    @Override
    public PessoaData toData(Pessoa pessoa) {
        if (pessoa == null) return null;

        return PessoaData.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .endereco(enderecoMapper.toData(pessoa.getEndereco()))
                .contato(contatoMapper.toData(pessoa.getContato()))
                .build();
    }
}
