package br.com.aplicacao.cadastro.pessoa.dominio.regras;

import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.core.regras.Regras;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

/**
 * Regras de negócio para a operação de exclusão de uma pessoa.
 *
 * <p>Esta classe define as regras que devem ser seguidas ao excluir uma pessoa do sistema.
 * Ela valida o ID da pessoa e realiza a exclusão através do serviço {@link PessoaService}.</p>
 *
 * <p>A anotação {@code @Named} é utilizada para que esta classe seja injetada como um bean gerenciado pelo Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Named
@RequiredArgsConstructor
public class DeletarPessoaRegras implements Regras<Pessoa> {

    private final PessoaService service;

    /**
     * Executa as regras de negócio para a operação de exclusão de uma pessoa.
     *
     * @param pessoa a pessoa a ser excluída
     * @return a pessoa excluída
     */
    @Override
    public Pessoa execute(Pessoa pessoa) {
        return new Pessoa(this.execute(pessoa.getId()));
    }

    /**
     * Executa as regras de negócio para a operação de exclusão de uma pessoa através do ID.
     *
     * @param id o ID da pessoa a ser excluída
     * @return o ID da pessoa excluída
     * @throws NullPointerException se o ID for nulo
     */
    public Long execute(Long id) {
        validarIdNulo(id);
        service.deletar(id);
        return id;
    }

    /**
     * Valida se o ID da pessoa é nulo.
     *
     * @param id o ID a ser validado
     * @throws NullPointerException se o ID for nulo
     */
    public void validarIdNulo(Long id) {
        if (id == null) {
            throw new NullPointerException("Id é obrigatório");
        }
    }
}
