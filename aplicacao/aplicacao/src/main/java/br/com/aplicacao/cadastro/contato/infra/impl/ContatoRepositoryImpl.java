package br.com.aplicacao.cadastro.contato.infra.impl;

import br.com.aplicacao.cadastro.contato.ContatoMapper;
import br.com.aplicacao.cadastro.contato.dominio.ContatoRepository;
import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.ContatoDataRepository;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import br.com.aplicacao.core.BaseMapper;
import br.com.aplicacao.core.infra.impl.BaseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação do repositório de contatos.
 *
 * <p>Esta classe fornece a implementação concreta da interface {@code ContatoRepository},
 * utilizando a infraestrutura do Spring Data JPA para realizar operações de persistência
 * e recuperação de dados.</p>
 *
 * <p>Esta classe é anotada com {@code @Repository} para indicar que é um componente de repositório do Spring
 * e com {@code @Transactional} para garantir que os métodos sejam executados dentro de uma transação.</p>
 *
 * <p>A anotação {@code @RequiredArgsConstructor} do Lombok é usada para gerar um construtor
 * que inicializa os campos finais {@code contatoMapper} e {@code contatoDataRepository}.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class ContatoRepositoryImpl extends BaseRepositoryImpl<Contato, ContatoData> implements ContatoRepository {

    /**
     * Mapper para converter entre {@code Contato} e {@code ContatoData}.
     */
    private final ContatoMapper contatoMapper;

    /**
     * Repositório JPA para a entidade {@code ContatoData}.
     */
    private final ContatoDataRepository contatoDataRepository;

    /**
     * Retorna o mapper utilizado para converter entre {@code Contato} e {@code ContatoData}.
     *
     * @return o mapper para conversão de entidades
     */
    @Override
    public BaseMapper<Contato, ContatoData> getMapper() {
        return contatoMapper;
    }

    /**
     * Retorna o repositório JPA utilizado para realizar operações de persistência com {@code ContatoData}.
     *
     * @return o repositório JPA para {@code ContatoData}
     */
    @Override
    public JpaRepository<ContatoData, Long> getRepository() {
        return contatoDataRepository;
    }
}
