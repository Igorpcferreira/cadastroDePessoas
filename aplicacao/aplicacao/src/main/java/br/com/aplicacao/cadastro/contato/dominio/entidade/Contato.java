package br.com.aplicacao.cadastro.contato.dominio.entidade;

import lombok.*;

/**
 * Classe que representa um contato com um telefone e um identificador.
 *
 * <p>Esta classe utiliza o Lombok para gerar automaticamente os métodos getters, setters,
 * um construtor padrão, um construtor com todos os argumentos e um padrão builder.
 * Além disso, possui um construtor adicional que aceita apenas o telefone.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    /**
     * Identificador único do contato.
     */
    private Long id;

    /**
     * Número de telefone do contato.
     */
    @Setter
    private String telefone;

    /**
     * Construtor que aceita apenas o telefone do contato.
     *
     * @param telefone o número de telefone do contato
     */
    public Contato(String telefone) {
        this.telefone = telefone;
    }
}
