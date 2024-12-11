package br.com.aplicacao.core.infra;

import br.com.aplicacao.core.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface genérica para definir operações básicas de um repositório.
 *
 * <p>A interface {@code BaseRepository} define métodos padrões para inclusão, alteração, exclusão e obtenção de
 * mapeadores e repositórios específicos para uma entidade de domínio ({@code DOMAIN}) e uma entidade de dados
 * ({@code DATA}).</p>
 *
 * <p>Os métodos definidos incluem operações comuns de CRUD (Create, Read, Update, Delete) que devem ser implementadas
 * por classes concretas para interagir com um banco de dados através de um repositório JPA.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     public interface PessoaRepository extends BaseRepository<Pessoa, PessoaData> {
 *         // Métodos específicos de operações adicionais para a entidade Pessoa
 *     }
 * }</pre>
 *
 * @param <DOMAIN> Tipo da entidade de domínio.
 * @param <DATA>   Tipo da entidade de dados (utilizada para persistência).
 * @see BaseMapper
 * @see JpaRepository
 */
public interface BaseRepository<DOMAIN, DATA> {

    /**
     * Método para incluir uma nova entidade de domínio no repositório.
     *
     * @param domain Entidade de domínio a ser incluída.
     * @return A entidade de domínio incluída no repositório após a operação.
     */
    DOMAIN incluir(DOMAIN domain);

    /**
     * Método para alterar uma entidade de domínio existente no repositório.
     *
     * @param domain Entidade de domínio a ser alterada.
     * @return A entidade de domínio alterada no repositório após a operação.
     */
    DOMAIN alterar(DOMAIN domain);

    /**
     * Método para excluir uma entidade de domínio do repositório pelo seu identificador.
     *
     * @param id Identificador da entidade de domínio a ser excluída.
     * @return O identificador da entidade de domínio excluída após a operação.
     */
    Long excluir(Long id);

    /**
     * Método para obter o mapeador utilizado para converter entre a entidade de domínio e a entidade de dados.
     *
     * @return O mapeador ({@link BaseMapper}) associado a este repositório.
     */
    BaseMapper<DOMAIN, DATA> getMapper();

    /**
     * Método para obter o repositório JPA que realiza as operações de persistência para a entidade de dados.
     *
     * @return O repositório JPA ({@link JpaRepository}) associado a este repositório.
     */
    JpaRepository<DATA, Long> getRepository();
}
