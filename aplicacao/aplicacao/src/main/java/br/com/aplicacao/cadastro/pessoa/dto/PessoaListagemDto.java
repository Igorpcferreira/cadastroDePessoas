package br.com.aplicacao.cadastro.pessoa.dto;

import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa uma Pessoa para listagem.
 *
 * <p>Este DTO é utilizado para apresentar informações resumidas de uma pessoa,
 * geralmente em operações de listagem ou apresentação de dados simplificados.</p>
 *
 * <p>O método estático {@link #from(Pessoa)} permite criar uma instância deste DTO
 * a partir de uma entidade {@link Pessoa}, preenchendo apenas os campos necessários
 * para a listagem, como identificador, nome, bairro e telefone.</p>
 *
 * <p>O método {@link #toEntity()} converte este DTO de listagem em uma entidade {@link Pessoa},
 * facilitando a integração com a camada de domínio da aplicação.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaListagemDto {

    /**
     * O identificador único da pessoa.
     */
    private Long id;

    /**
     * O nome da pessoa.
     */
    private String nome;

    /**
     * O bairro onde a pessoa reside.
     */
    private String bairro;

    /**
     * O telefone de contato da pessoa.
     */
    private String telefone;

    /**
     * Cria uma instância de {@code PessoaListagemDto} a partir de uma entidade {@link Pessoa}.
     *
     * @param domain a entidade {@link Pessoa} da qual será criado o DTO de listagem
     * @return uma instância de {@code PessoaListagemDto} com os dados preenchidos a partir da entidade
     */
    public static PessoaListagemDto from(Pessoa domain) {
        if (domain == null) return null;
        return PessoaListagemDto.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .bairro(domain.getEndereco().getBairro())
                .telefone(domain.getContato().getTelefone())
                .build();
    }

    /**
     * Converte este DTO de listagem em uma entidade {@link Pessoa}.
     *
     * @return a entidade {@link Pessoa} correspondente a este DTO de listagem
     */
    public Pessoa toEntity() {
        return Pessoa.builder()
                .id(id)
                .nome(nome)
                .build();
    }
}
