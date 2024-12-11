package br.com.aplicacao.core.infra.impl;

import br.com.aplicacao.core.infra.BaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementação abstrata da interface {@link BaseRepository} que fornece operações básicas de persistência.
 *
 * <p>A classe {@code BaseRepositoryImpl} implementa métodos genéricos para inclusão, alteração e exclusão de entidades
 * através de um repositório de dados JPA. Esses métodos utilizam um {@link BaseMapper} para converter entre a entidade
 * de domínio ({@code DOMAIN}) e a entidade de dados ({@code DATA}), além de capturar exceções de persistência e
 * registrar logs detalhados.</p>
 *
 * <p>Os métodos {@link #incluir(Object)}, {@link #alterar(Object)} e {@link #excluir(Long)} são implementados para
 * realizar operações comuns de CRUD (Create, Read, Update, Delete) em uma entidade específica.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     @Repository
 *     public class PessoaRepositoryImpl extends BaseRepositoryImpl<Pessoa, PessoaData> implements PessoaRepository {
 *         // Implementação personalizada do repositório de pessoa
 *     }
 * }</pre>
 *
 * @param <DOMAIN> Tipo da entidade de domínio.
 * @param <DATA>   Tipo da entidade de dados (utilizada para persistência).
 * @see BaseRepository
 * @see BaseMapper
 */
public abstract class BaseRepositoryImpl<DOMAIN, DATA> implements BaseRepository<DOMAIN, DATA> {

    private static final Logger logger = LogManager.getLogger(BaseRepositoryImpl.class.getName());

    /**
     * Método para incluir uma nova entidade de domínio no repositório.
     *
     * @param domain Entidade de domínio a ser incluída.
     * @return A entidade de domínio incluída no repositório após a operação.
     * @throws RuntimeException Se ocorrer um erro durante a operação de inclusão.
     */
    public DOMAIN incluir(DOMAIN domain) {
        try {
            DATA data = getMapper().toData(domain);
            data = getRepository().save(data);
            return getMapper().toDomain(data);
        } catch (Exception e) {
            logger.error("Erro ao inserir registro:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para alterar uma entidade de domínio existente no repositório.
     *
     * @param domain Entidade de domínio a ser alterada.
     * @return A entidade de domínio alterada no repositório após a operação.
     * @throws RuntimeException Se ocorrer um erro durante a operação de alteração.
     */
    public DOMAIN alterar(DOMAIN domain) {
        try {
            DATA data = getMapper().toData(domain);
            data = getRepository().save(data);
            return getMapper().toDomain(data);
        } catch (Exception e) {
            logger.error("Erro ao alterar registro:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para excluir uma entidade de domínio do repositório pelo seu identificador.
     *
     * @param id Identificador da entidade de domínio a ser excluída.
     * @return O identificador da entidade de domínio excluída após a operação.
     * @throws RuntimeException Se ocorrer um erro durante a operação de exclusão.
     */
    public Long excluir(Long id) {
        try {
            getRepository().deleteById(id);
            return id;
        } catch (Exception e) {
            logger.error("Erro ao excluir registro:", e);
            throw new RuntimeException(e);
        }
    }
}
