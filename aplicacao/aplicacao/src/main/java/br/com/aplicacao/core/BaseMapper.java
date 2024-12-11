package br.com.aplicacao.core;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe abstrata que define operações de mapeamento entre entidades de domínio ({@code DOMINIO}) e dados de persistência
 * ({@code DATA}).
 *
 * <p>A classe {@code BaseMapper} oferece métodos para converter listas e conjuntos de entidades de domínio para dados
 * de persistência e vice-versa. Estes métodos são úteis para mapear coleções de entidades ao lidar com camadas de
 * infraestrutura ou persistência de dados.</p>
 *
 * <p>Os métodos {@code toDomain} e {@code toData} devem ser implementados pelas classes concretas que estendem
 * {@code BaseMapper}, para definir como converter uma instância única de {@code DATA} para {@code DOMINIO} e
 * vice-versa.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     public class PessoaMapper extends BaseMapper<Pessoa, PessoaData> {
 *
 *         public Pessoa toDomain(PessoaData pessoaData) {
 *             // Implementação para converter PessoaData para Pessoa
 *         }
 *
 *         public PessoaData toData(Pessoa pessoa) {
 *             // Implementação para converter Pessoa para PessoaData
 *         }
 *     }
 * }</pre>
 *
 * @param <DOMINIO> Tipo da entidade de domínio ({@code DOMINIO}) que representa os objetos de negócio da aplicação.
 * @param <DATA>    Tipo dos dados de persistência ({@code DATA}) que representam as estruturas de armazenamento de
 *                  dados da aplicação.
 */
public abstract class BaseMapper<DOMINIO, DATA> {

    /**
     * Converte um único objeto de dados de persistência ({@code DATA}) para sua correspondente entidade de domínio
     * ({@code DOMINIO}).
     *
     * @param data Objeto de dados de persistência a ser convertido para entidade de domínio.
     * @return A entidade de domínio correspondente ao objeto de dados de persistência fornecido.
     */
    public abstract DOMINIO toDomain(DATA data);

    /**
     * Converte uma única entidade de domínio ({@code DOMINIO}) para sua correspondente estrutura de dados de
     * persistência ({@code DATA}).
     *
     * @param dominio Entidade de domínio a ser convertida para objeto de dados de persistência.
     * @return A estrutura de dados de persistência correspondente à entidade de domínio fornecida.
     */
    public abstract DATA toData(DOMINIO dominio);

    /**
     * Converte uma lista de objetos de dados de persistência ({@code DATA}) para uma lista de entidades de domínio
     * ({@code DOMINIO}).
     *
     * @param datas Lista de objetos de dados de persistência a serem convertidos.
     * @return Lista contendo as entidades de domínio correspondentes aos dados de persistência fornecidos.
     */
    public List<DOMINIO> toDomain(List<DATA> datas) {
        if (CollectionUtils.isEmpty(datas)) return new ArrayList<>();
        return datas.stream().map(this::toDomain).collect(Collectors.toList());
    }

    /**
     * Converte uma lista de entidades de domínio ({@code DOMINIO}) para uma lista de estruturas de dados de
     * persistência ({@code DATA}).
     *
     * @param dominios Lista de entidades de domínio a serem convertidas.
     * @return Lista contendo os objetos de dados de persistência correspondentes às entidades de domínio fornecidas.
     */
    public List<DATA> toData(List<DOMINIO> dominios) {
        if (CollectionUtils.isEmpty(dominios)) return new ArrayList<>();
        return dominios.stream().map(this::toData).collect(Collectors.toList());
    }

    /**
     * Converte um conjunto de objetos de dados de persistência ({@code DATA}) para um conjunto de entidades de domínio
     * ({@code DOMINIO}).
     *
     * @param datas Conjunto de objetos de dados de persistência a serem convertidos.
     * @return Conjunto contendo as entidades de domínio correspondentes aos dados de persistência fornecidos.
     */
    public Set<DOMINIO> toDomain(Set<DATA> datas) {
        if (CollectionUtils.isEmpty(datas)) return new HashSet<>();
        return datas.stream().map(this::toDomain).collect(Collectors.toSet());
    }

    /**
     * Converte um conjunto de entidades de domínio ({@code DOMINIO}) para um conjunto de estruturas de dados de
     * persistência ({@code DATA}).
     *
     * @param dominios Conjunto de entidades de domínio a serem convertidas.
     * @return Conjunto contendo os objetos de dados de persistência correspondentes às entidades de domínio fornecidas.
     */
    public Set<DATA> toData(Set<DOMINIO> dominios) {
        if (CollectionUtils.isEmpty(dominios)) return new HashSet<>();
        return dominios.stream().map(this::toData).collect(Collectors.toSet());
    }
}
