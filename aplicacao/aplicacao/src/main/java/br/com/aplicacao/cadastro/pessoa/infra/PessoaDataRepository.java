package br.com.aplicacao.cadastro.pessoa.infra;

import br.com.aplicacao.cadastro.pessoa.infra.data.PessoaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório Spring Data JPA para acessar e manipular dados da entidade {@link PessoaData}.
 * Esta interface estende {@link JpaRepository} para operações básicas de CRUD e {@link JpaSpecificationExecutor}
 * para suporte à execução de consultas dinâmicas com especificações.
 *
 * <p>Esta interface define métodos personalizados para consultas específicas utilizando JPQL ({@link Query}).</p>
 *
 * <p>As consultas JPQL definidas nesta interface verificam a existência de registros com o mesmo nome e telefone
 * associados a entidades {@link PessoaData}, garantindo a integridade dos dados.</p>
 *
 * <p>Esta classe é anotada com {@link Repository} para ser reconhecida como um componente de repositório pelo Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Repository
public interface PessoaDataRepository extends JpaRepository<PessoaData, Long>, JpaSpecificationExecutor<PessoaData> {

    /**
     * Verifica se um telefone já está cadastrado para outra pessoa, excluindo o ID especificado.
     *
     * @param telefone O telefone a ser verificado.
     * @param id       O ID a ser excluído da verificação.
     * @return {@code true} se o telefone já está cadastrado para outra pessoa, {@code false} caso contrário.
     */
    @Query("select case when count(pessoa.id) > 0 then true else false end " +
            "from PessoaData pessoa " +
            "where pessoa.contato.telefone = ?1 " +
            "and pessoa.id <> ?2")
    boolean telefoneJaCadastradoSemSerEu(String telefone, Long id);


    /**
     * Verifica se um telefone já está cadastrado para alguma pessoa.
     *
     * @param telefone O telefone a ser verificado.
     * @return {@code true} se o telefone já está cadastrado para alguma pessoa, {@code false} caso contrário.
     */
    @Query("select case when count(pessoa.id) > 0 then true else false end " +
            "from PessoaData pessoa " +
            "where pessoa.contato.telefone = ?1 ")
    boolean telefoneJaCadastrado(String telefone);
}
