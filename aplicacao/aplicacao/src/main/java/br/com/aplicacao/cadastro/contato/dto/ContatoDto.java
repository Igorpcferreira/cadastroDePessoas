package br.com.aplicacao.cadastro.contato.dto;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade {@code Contato}.
 *
 * <p>Esta classe é utilizada para transferir dados de contatos entre camadas da aplicação.
 * A anotação do Lombok {@code @Data} é usada para gerar automaticamente os métodos getters, setters,
 * {@code toString()}, {@code equals()} e {@code hashCode()}. A anotação {@code @Builder} permite
 * a construção de objetos {@code ContatoDto} utilizando o padrão Builder. A anotação {@code @AllArgsConstructor}
 * gera um construtor que inicializa todos os campos.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Data
@Builder
@AllArgsConstructor
public class ContatoDto {

    /**
     * Identificador único do contato.
     */
    private Long id;

    /**
     * Número de telefone do contato.
     */
    private String telefone;

    /**
     * Construtor que aceita apenas o telefone do contato.
     *
     * @param telefone o número de telefone do contato
     */
    public ContatoDto(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Converte este DTO em uma entidade {@code Contato}.
     *
     * @return um objeto {@code Contato} com os dados deste DTO
     */
    public Contato toEntity() {
        return Contato.builder()
                .id(id)
                .telefone(telefone)
                .build();
    }
}
