package br.com.aplicacao.cadastro.pessoa.dominio;

import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import br.com.aplicacao.core.infra.BaseRepository;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Interface que define operações de persistência para entidades de Pessoa.
 *
 * <p>Esta interface estende {@link BaseRepository}, especializada para a entidade {@link Pessoa}
 * e seu respectivo {@link PessoaData}.</p>
 *
 * <p>Ela define métodos para consultar, listar, verificar a utilização de contatos
 * e outras operações relacionadas à entidade Pessoa.</p>
 *
 * <p>Implementações desta interface devem prover lógica para interagir com o repositório
 * de dados subjacente e realizar operações CRUD (Create, Read, Update, Delete).</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
public interface PessoaRepository extends BaseRepository<Pessoa, PessoaData> {

    /**
     * Consulta uma pessoa pelo seu identificador único.
     *
     * @param id o identificador único da pessoa
     * @return um {@link Optional} contendo a pessoa encontrada, se presente
     */
    Optional<Pessoa> consultar(Long id);

    /**
     * Retorna uma página de pessoas.
     *
     * @return uma {@link Page} contendo as pessoas encontradas
     */
    Page<Pessoa> listar();

    /**
     * Verifica se o contato de uma pessoa já está sendo utilizado por outra pessoa.
     *
     * @param pessoa a pessoa cujo contato será verificado
     * @return true se o contato já está sendo utilizado, false caso contrário
     */
    boolean contatoJaUtilizado(Pessoa pessoa);

    /**
     * Verifica se o contato de uma pessoa já está sendo utilizado por outra pessoa,
     * excluindo a própria pessoa da verificação.
     *
     * @param pessoa a pessoa cujo contato será verificado
     * @return true se o contato já está sendo utilizado por outra pessoa, false caso contrário
     */
    boolean contatoJaUtilizadoSemSerEu(Pessoa pessoa);
}
