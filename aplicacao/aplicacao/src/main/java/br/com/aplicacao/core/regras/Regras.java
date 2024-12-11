package br.com.aplicacao.core.regras;

/**
 * Interface que define o contrato para classes que implementam regras de negócio.
 *
 * <p>A interface {@code Regras} especifica um único método {@code execute} que recebe uma entidade de domínio
 * ({@code DOMINIO}) como parâmetro e retorna a mesma entidade após a aplicação das regras de negócio.</p>
 *
 * <p>Implementações desta interface devem ser utilizadas para encapsular lógica de negócio específica, como validações,
 * transformações ou qualquer operação que seja necessária antes de persistir ou manipular uma entidade de domínio.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     public class ValidarDadosPessoaRegras implements Regras<Pessoa> {
 *
 *         public Pessoa execute(Pessoa pessoa) {
 *             // Implementação das regras de validação para a entidade Pessoa
 *             // Retorna a própria entidade após aplicar as validações
 *         }
 *     }
 * }</pre>
 *
 * @param <DOMINIO> Tipo da entidade de domínio sobre a qual as regras serão aplicadas.
 */
public interface Regras<DOMINIO> {

    /**
     * Método que define a execução das regras de negócio para uma entidade de domínio.
     *
     * @param dominio Entidade de domínio sobre a qual as regras serão aplicadas.
     * @return A entidade de domínio após a aplicação das regras de negócio.
     */
    DOMINIO execute(DOMINIO dominio);
}
