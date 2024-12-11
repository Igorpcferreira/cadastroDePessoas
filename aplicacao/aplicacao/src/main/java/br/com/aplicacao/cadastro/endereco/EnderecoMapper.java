package br.com.aplicacao.cadastro.endereco;

import br.com.aplicacao.cadastro.endereco.dominio.entidade.Endereco;
import br.com.aplicacao.cadastro.endereco.infra.data.EnderecoData;
import br.com.aplicacao.core.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre {@code Endereco} e {@code EnderecoData}.
 *
 * <p>Esta classe estende {@code BaseMapper}, fornecendo métodos para converter entre
 * a entidade de domínio {@code Endereco} e a entidade de persistência {@code EnderecoData}.</p>
 *
 * <p>A anotação {@code @Component} indica que esta classe é um componente gerenciado pelo Spring.</p>
 *
 * <p>Autor: João Cleber Dias Maciel</p>
 */
@Component
public class EnderecoMapper extends BaseMapper<Endereco, EnderecoData> {

    /**
     * Converte um objeto {@code EnderecoData} em um objeto {@code Endereco}.
     *
     * @param enderecoData o objeto {@code EnderecoData} a ser convertido
     * @return o objeto {@code Endereco} resultante da conversão
     */
    @Override
    public Endereco toDomain(EnderecoData enderecoData) {
        if (enderecoData == null) return null;

        return Endereco.builder()
                .id(enderecoData.getId())
                .bairro(enderecoData.getBairro())
                .build();
    }

    /**
     * Converte um objeto {@code Endereco} em um objeto {@code EnderecoData}.
     *
     * @param endereco o objeto {@code Endereco} a ser convertido
     * @return o objeto {@code EnderecoData} resultante da conversão
     */
    @Override
    public EnderecoData toData(Endereco endereco) {
        if (endereco == null) return null;

        return EnderecoData.builder()
                .id(endereco.getId())
                .bairro(endereco.getBairro())
                .build();
    }
}
