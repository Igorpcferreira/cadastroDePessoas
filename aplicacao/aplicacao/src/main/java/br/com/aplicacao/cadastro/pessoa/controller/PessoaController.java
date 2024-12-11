package br.com.aplicacao.cadastro.pessoa.controller;

import br.com.aplicacao.cadastro.pessoa.aplicacao.PessoaService;
import br.com.aplicacao.cadastro.pessoa.dominio.entidade.Pessoa;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.AlterarPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.DeletarPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dominio.regras.IncluirPessoaRegras;
import br.com.aplicacao.cadastro.pessoa.dto.PessoaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas à entidade {@code Pessoa}.
 *
 * <p>Este controlador oferece endpoints para consultar, incluir, alterar, deletar e listar pessoas.
 * Utiliza as regras de negócio {@code IncluirPessoaRegras}, {@code AlterarPessoaRegras} e {@code DeletarPessoaRegras}
 * para garantir a integridade dos dados. A anotação {@code @RestController} indica que esta classe é um controlador REST.
 * A anotação {@code @CrossOrigin} permite requisições de origens diferentes. O caminho base para os endpoints é {@code /cadastro/pessoa}.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cadastro/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;
    private final IncluirPessoaRegras inserirPessoaRegras;
    private final AlterarPessoaRegras alterarPessoaRegras;
    private final DeletarPessoaRegras deletarPessoaRegras;

    /**
     * Consulta uma pessoa pelo seu identificador único.
     *
     * @param id o identificador único da pessoa a ser consultada
     * @return ResponseEntity contendo a pessoa encontrada, se existir
     */
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Pessoa> consultar(@PathVariable Long id) {
        Pessoa entidade = pessoaService.consultar(id);
        return ResponseEntity.ok(entidade);
    }

    /**
     * Inclui uma nova pessoa.
     *
     * @param pessoaDto os dados da pessoa a ser incluída
     * @return ResponseEntity contendo o ID da pessoa incluída
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> incluir(@RequestBody PessoaDto pessoaDto) {
        Pessoa domain = pessoaDto.toEntity();
        return ResponseEntity.ok(inserirPessoaRegras.execute(domain).getId());
    }

    /**
     * Altera os dados de uma pessoa existente.
     *
     * @param pessoaDto os novos dados da pessoa a ser alterada
     * @return ResponseEntity contendo o ID da pessoa alterada
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> alterar(@RequestBody @Valid PessoaDto pessoaDto) {
        Pessoa domain = pessoaDto.toEntity();
        if (domain.getId() == null) domain.setId(pessoaDto.getId());
        return ResponseEntity.ok(alterarPessoaRegras.execute(domain).getId());
    }

    /**
     * Deleta uma pessoa pelo seu identificador único.
     *
     * @param id o identificador único da pessoa a ser deletada
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        deletarPessoaRegras.execute(id);
    }

    /**
     * Lista todas as pessoas cadastradas.
     *
     * @return ResponseEntity contendo a lista de pessoas encontradas
     */
    @GetMapping("listar")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Pessoa>> listar() {
        return ResponseEntity.ok(pessoaService.listar().getContent());
    }
}
