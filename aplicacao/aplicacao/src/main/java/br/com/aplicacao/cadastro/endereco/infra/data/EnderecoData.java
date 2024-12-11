package br.com.aplicacao.cadastro.endereco.infra.data;

import jakarta.persistence.*;
import lombok.*;

/**
 * Classe de entidade que representa um endereço na base de dados.
 *
 * <p>Esta classe é mapeada para a tabela {@code cadastro_endereco} no banco de dados.
 * A anotação do Lombok {@code @Getter} é usada para gerar automaticamente os métodos getters.
 * A anotação {@code @Builder} permite a construção de objetos {@code EnderecoData} utilizando o padrão Builder.
 * As anotações {@code @AllArgsConstructor} e {@code @NoArgsConstructor} geram, respectivamente,
 * um construtor que inicializa todos os campos e um construtor padrão sem argumentos.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cadastro_endereco")
public class EnderecoData {

    /**
     * Identificador único do endereço.
     */
    @Id
    @Column(name = "id_endereco")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Bairro do endereço.
     */
    @Setter
    @Column(name = "bairro")
    private String bairro;
}
