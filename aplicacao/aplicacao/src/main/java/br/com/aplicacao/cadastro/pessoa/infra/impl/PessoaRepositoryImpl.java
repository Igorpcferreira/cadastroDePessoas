package br.com.aplicacao.cadastro.pessoa.infra.impl;

import br.com.aplicacao.cadastro.pessoa.PessoaMapper;
import br.com.aplicacao.cadastro.pessoa.dominio.PessoaRepository;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.infra.PessoaDataRepository;
import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import br.com.aplicacao.core.BaseMapper;
import br.com.aplicacao.core.infra.impl.BaseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementação concreta da interface {@link PessoaRepository} que utiliza o Spring Data JPA para acessar e manipular
 * dados de {@link Pessoa}.
 *
 * <p>Esta classe estende {@link BaseRepositoryImpl} e implementa todos os métodos definidos em {@link PessoaRepository}.
 * Ela utiliza o {@link PessoaMapper} para converter entre objetos de domínio {@link Pessoa} e entidades JPA
 * {@link PessoaData}. O repositório JPA utilizado é {@link PessoaDataRepository}, injetado através de construtor.</p>
 *
 * <p>As operações de consulta, inclusão, alteração e exclusão são realizadas através dos métodos herdados de
 * {@link BaseRepositoryImpl}, garantindo o uso consistente e eficiente das funcionalidades providas pelo Spring Data JPA.</p>
 *
 * <p>Esta classe é anotada com {@link Repository} para ser reconhecida como um componente de repositório pelo Spring,
 * {@link Transactional} para garantir que todas as operações sejam transacionais, e {@link RequiredArgsConstructor}
 * para gerar um construtor com injeção de dependências para os campos marcados como {@code final}.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class PessoaRepositoryImpl extends BaseRepositoryImpl<Pessoa, PessoaData> implements PessoaRepository {

    private final PessoaMapper pessoaMapper;
    private final PessoaDataRepository pessoaDataRepository;

    /**
     * Retorna o {@link BaseMapper} utilizado para converter entre {@link Pessoa} (domínio) e {@link PessoaData} (entidade).
     *
     * @return O {@link BaseMapper} {@link PessoaMapper}.
     */
    @Override
    public BaseMapper<Pessoa, PessoaData> getMapper() {
        return pessoaMapper;
    }

    /**
     * Retorna o repositório JPA {@link JpaRepository} utilizado para acessar dados de {@link PessoaData}.
     *
     * @return O repositório JPA {@link JpaRepository} {@link PessoaDataRepository}.
     */
    @Override
    public JpaRepository<PessoaData, Long> getRepository() {
        return pessoaDataRepository;
    }

    /**
     * Consulta uma pessoa pelo seu ID.
     *
     * @param id O ID da pessoa a ser consultada.
     * @return Um {@link Optional} que pode conter a {@link Pessoa} encontrada, convertida de {@link PessoaData},
     * ou vazio se não encontrada.
     */
    @Override
    public Optional<Pessoa> consultar(Long id) {
        return pessoaDataRepository.findById(id).map(pessoaMapper::toDomain);
    }

    /**
     * Lista todas as pessoas cadastradas.
     *
     * @return Uma {@link Page} contendo as {@link Pessoa} encontradas, convertidas de {@link PessoaData}.
     */
    @Override
    public Page<Pessoa> listar() {
        return pessoaDataRepository.findAll(Pageable.unpaged()).map(pessoaMapper::toDomain);
    }

    /**
     * Verifica se o telefone de contato já está sendo utilizado por outra pessoa.
     *
     * @param pessoa A {@link Pessoa} contendo o telefone de contato a ser verificado.
     * @return {@code true} se o telefone já está em uso por outra pessoa, {@code false} caso contrário.
     */
    @Override
    public boolean contatoJaUtilizado(Pessoa pessoa) {
        return pessoaDataRepository.telefoneJaCadastrado(pessoaMapper.toData(pessoa).getContato().getTelefone());
    }

    /**
     * Verifica se o telefone de contato já está sendo utilizado por outra pessoa, excluindo a própria pessoa.
     *
     * @param pessoa A {@link Pessoa} contendo o telefone de contato a ser verificado.
     * @return {@code true} se o telefone já está em uso por outra pessoa, excluindo a própria pessoa,
     * {@code false} caso contrário.
     */
    @Override
    public boolean contatoJaUtilizadoSemSerEu(Pessoa pessoa) {
        return pessoaDataRepository.telefoneJaCadastradoSemSerEu(pessoaMapper.toData(pessoa).getContato().getTelefone(), pessoa.getId());
    }
}
