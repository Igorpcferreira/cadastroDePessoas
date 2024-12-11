package br.com.aplicacao.cadastro.contato.dominio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeração que define a expressão regular para validar formatos de números de telefone.
 *
 * <p>Esta enumeração contém uma única constante que define a máscara de regex para números de telefone.
 * A anotação do Lombok {@code @AllArgsConstructor} é utilizada para gerar um construtor que inicializa
 * a expressão regular.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@AllArgsConstructor
public enum TelefoneEnum {
    /**
     * Expressão regular para validar o formato de números de telefone.
     * O formato esperado é: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
     */
    TELEFONE_MASCARA_REGEX("\\(\\d{2}\\) \\d{4,5}-\\d{4}");

    /**
     * A expressão regular associada à máscara de telefone.
     */
    @Getter
    private final String regex;
}
