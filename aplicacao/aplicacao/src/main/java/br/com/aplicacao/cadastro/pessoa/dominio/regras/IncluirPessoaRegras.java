package br.com.aplicacao.cadastro.pessoa.dominio.regras;

import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.dominio.enums.PessoaEnum;
import br.com.aplicacao.core.regras.Regras;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

import static br.com.aplicacao.cadastro.contato.dominio.enums.TelefoneEnum.TELEFONE_MASCARA_REGEX;

/**
 * Regras de negócio para a operação de inclusão de uma pessoa.
 *
 * <p>Esta classe define as regras que devem ser seguidas ao incluir uma nova pessoa no sistema.
 * Ela valida o nome, telefone de contato e se o contato já está cadastrado antes de realizar a inclusão,
 * utilizando o serviço {@link PessoaService}.</p>
 *
 * <p>A anotação {@code @Named} é utilizada para que esta classe seja injetada como um bean gerenciado pelo Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Named
@RequiredArgsConstructor
public class IncluirPessoaRegras implements Regras<Pessoa> {

    private final PessoaService service;

    /**
     * Executa as regras de negócio para a operação de inclusão de uma pessoa.
     *
     * @param pessoa a pessoa a ser incluída
     * @return a pessoa incluída
     */
    @Override
    public Pessoa execute(Pessoa pessoa) {
        validarNome(pessoa);
        validarTelefoneContato(pessoa);
        validarContatoJaCadastrado(pessoa);
        return service.incluir(pessoa);
    }

    /**
     * Valida o nome da pessoa.
     *
     * @param pessoa a pessoa a ser validada
     */
    public void validarNome(Pessoa pessoa) {
        validarNomeNulo(pessoa);
        validarTamanhoNome(pessoa);
        tratarNomeComEspacosEmBranco(pessoa);
    }

    /**
     * Verifica se o nome da pessoa é nulo.
     *
     * @param pessoa a pessoa a ser validada
     * @throws IllegalArgumentException se o nome for nulo, vazio ou composto apenas por espaços em branco
     */
    public void validarNomeNulo(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty() || pessoa.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }

    /**
     * Valida o tamanho máximo do nome da pessoa.
     *
     * @param pessoa a pessoa a ser validada
     * @throws IllegalArgumentException se o nome exceder o tamanho máximo permitido
     */
    public void validarTamanhoNome(Pessoa pessoa) {
        if (pessoa.getNome().length() > PessoaEnum.NOME_TAMANHO_MAXIMO.getValor()) {
            throw new IllegalArgumentException("O nome não deve ter mais de 10 caracteres");
        }
    }

    /**
     * Remove espaços em branco desnecessários do nome da pessoa.
     *
     * @param pessoa a pessoa cujo nome será tratado
     */
    public void tratarNomeComEspacosEmBranco(Pessoa pessoa) {
        pessoa.setNome(pessoa.getNome().trim());
    }

    /**
     * Valida o telefone de contato da pessoa.
     *
     * @param pessoa a pessoa a ser validada
     */
    public void validarTelefoneContato(Pessoa pessoa) {
        verificarTelefoneNulo(pessoa);
        verificarFormatoTelefone(pessoa);
    }

    /**
     * Verifica se o telefone de contato da pessoa é nulo.
     *
     * @param pessoa a pessoa a ser validada
     * @throws IllegalArgumentException se o telefone for nulo, vazio ou composto apenas por espaços em branco
     */
    public void verificarTelefoneNulo(Pessoa pessoa) {
        if (pessoa.getContato().getTelefone() == null || pessoa.getContato().getTelefone().isEmpty() || pessoa.getContato().getTelefone().isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
    }

    /**
     * Verifica se o formato do telefone de contato da pessoa é válido.
     *
     * @param pessoa a pessoa a ser validada
     * @throws IllegalArgumentException se o formato do telefone for inválido
     */
    public void verificarFormatoTelefone(Pessoa pessoa) {
        if (!pessoa.getContato().getTelefone().matches(TELEFONE_MASCARA_REGEX.getRegex())) {
            throw new IllegalArgumentException("Telefone inválido");
        }
    }

    /**
     * Valida se o contato da pessoa já está cadastrado no sistema.
     *
     * @param pessoa a pessoa a ser validada
     * @throws IllegalArgumentException se o contato já estiver cadastrado
     */
    public void validarContatoJaCadastrado(Pessoa pessoa) {
        if (service.contatoJaUtilizado(pessoa)) {
            throw new IllegalArgumentException("Contato já cadastrado");
        }
    }
}
