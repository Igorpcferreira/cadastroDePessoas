package br.com.aplicacao.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada para indicar que um recurso não foi encontrado.
 *
 * <p>A {@code NaoEncontradoException} é uma subclasse de {@link RuntimeException} que é lançada para indicar que um
 * recurso procurado não foi encontrado durante uma operação.</p>
 *
 * <p>Esta exceção utiliza a anotação {@link ResponseStatus} para configurar o código de status HTTP como
 * {@link HttpStatus#NOT_FOUND} (404) quando lançada em um controlador Spring MVC.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     public Pessoa consultar(Long id) {
 *         Optional<Pessoa> pessoa = repository.findById(id);
 *         return pessoa.orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
 *     }
 * }</pre>
 *
 * @see ResponseStatus
 * @see HttpStatus#NOT_FOUND
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NaoEncontradoException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem descritiva da exceção.
     *
     * @param mensagem Mensagem que descreve a causa da exceção.
     */
    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
