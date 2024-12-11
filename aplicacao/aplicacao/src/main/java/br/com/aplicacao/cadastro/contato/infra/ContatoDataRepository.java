package br.com.aplicacao.cadastro.contato.infra;

import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade {@code ContatoData}.
 *
 * <p>Esta interface estende {@code JpaRepository} e {@code JpaSpecificationExecutor} para fornecer
 * métodos de persistência e critérios de consulta para a entidade {@code ContatoData}.</p>
 *
 * <p>A anotação {@code @Repository} indica que esta interface é um componente de repositório do Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Repository
public interface ContatoDataRepository extends JpaRepository<ContatoData, Long>, JpaSpecificationExecutor<ContatoData> {
}
