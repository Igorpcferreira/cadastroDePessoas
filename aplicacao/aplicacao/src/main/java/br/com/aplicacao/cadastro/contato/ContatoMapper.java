package br.com.aplicacao.cadastro.contato;

import br.com.aplicacao.cadastro.contato.dominio.entidade.Contato;
import br.com.aplicacao.cadastro.contato.infra.data.ContatoData;
import br.com.aplicacao.core.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre {@code Contato} e {@code ContatoData}.
 *
 * <p>Esta classe estende {@code BaseMapper}, fornecendo métodos para converter entre
 * a entidade de domínio {@code Contato} e a entidade de persistência {@code ContatoData}.</p>
 *
 * <p>A anotação {@code @Component} indica que esta classe é um componente gerenciado pelo Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Component
public class ContatoMapper extends BaseMapper<Contato, ContatoData> {

    /**
     * Converte um objeto {@code ContatoData} em um objeto {@code Contato}.
     *
     * @param contatoData o objeto {@code ContatoData} a ser convertido
     * @return o objeto {@code Contato} resultante da conversão
     */
    @Override
    public Contato toDomain(ContatoData contatoData) {
        if (contatoData == null) return null;

        return Contato.builder()
                .id(contatoData.getId())
                .telefone(contatoData.getTelefone())
                .build();
    }

    /**
     * Converte um objeto {@code Contato} em um objeto {@code ContatoData}.
     *
     * @param contato o objeto {@code Contato} a ser convertido
     * @return o objeto {@code ContatoData} resultante da conversão
     */
    @Override
    public ContatoData toData(Contato contato) {
        if (contato == null) return null;

        return ContatoData.builder()
                .id(contato.getId())
                .telefone(contato.getTelefone())
                .build();
    }
}
