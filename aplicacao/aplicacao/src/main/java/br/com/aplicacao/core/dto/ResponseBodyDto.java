package br.com.aplicacao.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Classe que representa um objeto de resposta genérico.
 *
 * <p>O {@code ResponseBodyDto} encapsula uma entidade genérica ({@code Object}) que será retornada como resposta de uma
 * requisição.</p>
 *
 * <p>Esta classe possui um único atributo, {@code entidade}, que armazena o objeto que será retornado no corpo da
 * resposta.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     // Criando um ResponseBodyDto com uma entidade do tipo String
 *     ResponseBodyDto response = new ResponseBodyDto("Exemplo de resposta");
 * }</pre>
 */
@Data
@AllArgsConstructor
public class ResponseBodyDto {

    /**
     * Objeto que representa a entidade a ser retornada como resposta.
     */
    private Object entidade;
}
