package br.com.aplicacao.cadastro.endereco.dominio.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um endereço com um identificador e bairro.
 *
 * <p>Esta classe utiliza o Lombok para gerar automaticamente os métodos getters, setters,
 * um construtor padrão, um construtor com todos os argumentos e um padrão builder.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Endereco {

    /**
     * Identificador único do endereço.
     */
    private Long id;

    /**
     * Bairro do endereço.
     */
    private String bairro;
}
