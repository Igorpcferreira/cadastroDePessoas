package br.com.aplicacao.cadastro.endereco.dto;

import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade {@code Endereco}.
 *
 * <p>Esta classe é utilizada para transferir dados de endereço entre camadas da aplicação.
 * A anotação do Lombok {@code @Data} é usada para gerar automaticamente os métodos getters, setters,
 * {@code toString()}, {@code equals()} e {@code hashCode()}. A anotação {@code @Builder} permite
 * a construção de objetos {@code EnderecoDto} utilizando o padrão Builder. A anotação {@code @AllArgsConstructor}
 * gera um construtor que inicializa todos os campos.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Data
@Builder
@AllArgsConstructor
public class EnderecoDto {

    /**
     * Identificador único do endereço.
     */
    private Long id;

    /**
     * Bairro do endereço.
     */
    private String bairro;

    /**
     * Converte este DTO em uma entidade {@code Endereco}.
     *
     * @return um objeto {@code Endereco} com os dados deste DTO
     */
    public Endereco toEntity() {
        return Endereco.builder()
                .id(id)
                .bairro(bairro)
                .build();
    }
}
