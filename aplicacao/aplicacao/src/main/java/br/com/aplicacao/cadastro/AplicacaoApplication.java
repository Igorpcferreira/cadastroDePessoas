package br.com.aplicacao.cadastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia a aplicação Spring Boot.
 *
 * <p>Esta classe utiliza a anotação {@link SpringBootApplication}, que combina várias anotações em uma única anotação
 * conveniente. Ela ativa a autoconfiguração da aplicação Spring Boot e define a base para a configuração da aplicação.
 * </p>
 *
 * <p>Responsável por inicializar a aplicação Spring Boot através do método {@link #main(String[]) main}.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     public static void main(String[] args) {
 *         SpringApplication.run(AplicacaoApplication.class, args);
 *     }
 * }</pre>
 *
 * @author [João Cleber Dias Maciel]
 * @see SpringApplication
 * @see SpringBootApplication
 */
@SpringBootApplication
public class AplicacaoApplication {

    /**
     * Método principal que inicia a execução da aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando passados para a aplicação.
     */
    public static void main(String[] args) {
        SpringApplication.run(AplicacaoApplication.class, args);
    }
}
