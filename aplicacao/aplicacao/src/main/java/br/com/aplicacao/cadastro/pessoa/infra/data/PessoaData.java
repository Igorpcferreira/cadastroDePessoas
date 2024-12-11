package br.com.aplicacao.cadastro.pessoa.infra.data;

import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import br.com.aplicacao.cadastro.endereco.infra.data.EnderecoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA que representa uma Pessoa no contexto de persistência de dados.
 *
 * <p>Esta classe mapeia a estrutura de dados de uma pessoa para ser persistida em um banco de dados relacional.
 * Ela utiliza anotações JPA para definir o mapeamento objeto-relacional (ORM), permitindo que a pessoa seja
 * armazenada e recuperada através de um repositório JPA.</p>
 *
 * <p>A tabela correspondente no banco de dados é especificada pela anotação {@link Table}, onde o nome da tabela
 * é definido como "cadastro_pessoa". Os atributos são mapeados para colunas específicas usando as anotações
 * {@link Column} e {@link JoinColumn}.</p>
 *
 * <p>Os relacionamentos com as entidades {@link EnderecoData} e {@link ContatoData} são definidos pelas anotações
 * {@link OneToOne} com {@link JoinColumn}, utilizando {@link CascadeType#ALL} para garantir que as operações
 * de persistência, atualização e remoção sejam propagadas automaticamente para as entidades relacionadas.</p>
 *
 * <p>Atributos como {@code id}, {@code nome}, {@code endereco} e {@code contato} são mapeados para suas respectivas
 * colunas no banco de dados, facilitando a integração entre o modelo de domínio da aplicação e sua representação
 * persistente.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cadastro_pessoa")
public class PessoaData {

    /**
     * O identificador único da pessoa.
     */
    @Id
    @Column(name = "id_pessoa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome da pessoa.
     */
    @Column(name = "nome")
    private String nome;

    /**
     * O endereço associado à pessoa.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private EnderecoData endereco;

    /**
     * O contato associado à pessoa.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contato")
    private ContatoData contato;
}
