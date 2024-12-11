package br.com.aplicacao.cadastro.pessoa.aplicacao;

import br.com.aplicacao.cadastro.pessoa.dominio.PessoaRepository;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.core.exceptions.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas operações de negócio relacionadas à entidade {@code Pessoa}.
 *
 * <p>Este serviço oferece métodos para incluir, alterar, deletar, consultar, listar e verificar a utilização de contatos
 * associados a uma pessoa. Utiliza a interface {@code PessoaRepository} para realizar operações de persistência.</p>
 *
 * <p>A anotação {@code @Service} indica que esta classe é um componente de serviço do Spring, responsável por conter a lógica de negócio.</p>
 *
 * <p>A anotação {@code @RequiredArgsConstructor} gera um construtor com todos os campos necessários para inicialização automática
 * da dependência {@code repository} através do Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;

    /**
     * Inclui uma nova pessoa.
     *
     * @param pessoa a pessoa a ser incluída
     * @return a pessoa incluída
     */
    public Pessoa incluir(Pessoa pessoa) {
        return repository.incluir(pessoa);
    }

    /**
     * Altera os dados de uma pessoa existente.
     *
     * @param pessoa a pessoa com os novos dados
     * @return a pessoa alterada
     */
    public Pessoa alterar(Pessoa pessoa) {
        return repository.alterar(pessoa);
    }

    /**
     * Deleta uma pessoa pelo seu identificador único.
     *
     * @param id o identificador único da pessoa a ser deletada
     */
    public void deletar(Long id) {
        repository.excluir(id);
    }

    /**
     * Consulta uma pessoa pelo seu identificador único.
     *
     * @param id o identificador único da pessoa a ser consultada
     * @return a pessoa encontrada
     * @throws NaoEncontradoException se a pessoa não for encontrada
     */
    public Pessoa consultar(Long id) {
        return repository.consultar(id).orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
    }

    /**
     * Lista todas as pessoas paginadas.
     *
     * @return uma página com as pessoas listadas
     */
    public Page<Pessoa> listar() {
        return repository.listar();
    }

    /**
     * Verifica se o contato de uma pessoa já está sendo utilizado por outra pessoa.
     *
     * @param pessoa a pessoa para verificar o contato
     * @return {@code true} se o contato já está sendo utilizado, {@code false} caso contrário
     */
    public boolean contatoJaUtilizado(Pessoa pessoa) {
        return repository.contatoJaUtilizado(pessoa);
    }

    /**
     * Verifica se o contato de uma pessoa já está sendo utilizado por outra pessoa, excluindo a própria pessoa da verificação.
     *
     * @param pessoa a pessoa para verificar o contato
     * @return {@code true} se o contato já está sendo utilizado por outra pessoa, {@code false} caso contrário
     */
    public boolean contatoJaUtilizadoSemSerEu(Pessoa pessoa) {
        return repository.contatoJaUtilizadoSemSerEu(pessoa);
    }
}
