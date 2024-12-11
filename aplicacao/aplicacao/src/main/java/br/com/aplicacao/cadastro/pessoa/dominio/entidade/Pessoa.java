package br.com.aplicacao.cadastro.pessoa.dominio.entidade;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import lombok.*;

/**
 * Representa uma entidade de Pessoa no sistema.
 *
 * <p>Esta classe contém informações como {@code id}, {@code nome}, {@code endereco} e {@code contato} de uma pessoa.</p>
 *
 * <p>A anotação {@code @Getter} do Lombok gera automaticamente os métodos getters para os campos.</p>
 * <p>A anotação {@code @Builder} do Lombok permite a construção fluente de objetos desta classe.</p>
 * <p>A anotação {@code @NoArgsConstructor} do Lombok gera um construtor padrão sem argumentos.</p>
 * <p>A anotação {@code @AllArgsConstructor} do Lombok gera um construtor com todos os argumentos.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Setter
    private Long id;
    @Setter
    private String nome;
    private Endereco endereco;
    private Contato contato;

    /**
     * Construtor que inicializa uma pessoa com um ID específico.
     *
     * @param id o identificador único da pessoa
     */
    public Pessoa(Long id) {
        this.id = id;
    }
}
