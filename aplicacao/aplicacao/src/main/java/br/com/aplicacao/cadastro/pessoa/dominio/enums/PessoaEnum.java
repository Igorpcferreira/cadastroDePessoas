package br.com.aplicacao.cadastro.pessoa.dominio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeração que define constantes relacionadas a pessoas.
 *
 * <p>Esta enumeração oferece valores constantes que podem ser utilizados para definir regras ou limitações relacionadas a pessoas.</p>
 *
 * <p>A anotação {@code @AllArgsConstructor} do Lombok gera um construtor com todos os argumentos.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@AllArgsConstructor
public enum PessoaEnum {

    /**
     * Define o tamanho máximo permitido para o nome de uma pessoa.
     */
    NOME_TAMANHO_MAXIMO(10);

    /**
     * O valor associado a esta constante.
     */
    @Getter
    private final int valor;
}
