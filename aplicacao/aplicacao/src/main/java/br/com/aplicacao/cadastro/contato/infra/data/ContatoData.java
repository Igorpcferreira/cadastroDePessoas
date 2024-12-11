package br.com.aplicacao.cadastro.contato.infra.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe de entidade que representa um contato na base de dados.
 *
 * <p>Esta classe é mapeada para a tabela {@code cadastro_contato} no banco de dados.
 * A anotação do Lombok {@code @Getter} é usada para gerar automaticamente os métodos getters.
 * A anotação {@code @Builder} permite a construção de objetos {@code ContatoData} utilizando o padrão Builder.
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
@Table(name = "cadastro_contato")
public class ContatoData {

    /**
     * Identificador único do contato.
     */
    @Id
    @Column(name = "id_contato")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número de telefone do contato.
     */
    @Column(name = "telefone")
    private String telefone;
}
