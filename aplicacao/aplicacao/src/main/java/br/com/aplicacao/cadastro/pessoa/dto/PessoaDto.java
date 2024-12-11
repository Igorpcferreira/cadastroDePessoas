package br.com.aplicacao.cadastro.pessoa.dto;

import br.com.aplicacao.cadastro.contato.dto.ContatoDto;
import br.com.aplicacao.cadastro.endereco.dto.EnderecoDto;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa uma Pessoa para operações de transferência de dados.
 *
 * <p>Este DTO contém os campos básicos de uma pessoa, incluindo identificador, nome,
 * dados de contato e endereço. É utilizado principalmente para transferir dados entre
 * camadas da aplicação ou para comunicação com APIs externas.</p>
 *
 * <p>O método {@link #toEntity()} converte este DTO em uma entidade {@link Pessoa},
 * facilitando a integração com a camada de domínio da aplicação.</p>
 *
 * <p>Este DTO é utilizado em operações de inclusão, alteração, consulta e listagem de pessoas.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDto {

    /**
     * O identificador único da pessoa.
     */
    private Long id;

    /**
     * O nome da pessoa.
     */
    private String nome;

    /**
     * O DTO que representa o contato da pessoa.
     */
    private ContatoDto contato;

    /**
     * O DTO que representa o endereço da pessoa.
     */
    private EnderecoDto endereco;

    /**
     * Converte este DTO em uma entidade Pessoa.
     *
     * @return a entidade Pessoa correspondente a este DTO
     */
    public Pessoa toEntity() {
        return Pessoa.builder()
                .id(id)
                .nome(nome)
                .endereco(endereco != null ? endereco.toEntity() : null)
                .contato(contato != null ? contato.toEntity() : null)
                .build();
    }
}
