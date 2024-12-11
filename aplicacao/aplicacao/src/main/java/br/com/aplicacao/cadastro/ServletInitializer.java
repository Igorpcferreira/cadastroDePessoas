package br.com.aplicacao.cadastro;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Classe utilizada para inicialização da aplicação Spring Boot em um ambiente de servlet.
 *
 * <p>Esta classe estende {@link SpringBootServletInitializer}, que é uma classe de suporte fornecida pelo Spring
 * Boot para configurar a aplicação como uma servlet web tradicional.</p>
 *
 * <p>O método {@link #configure(SpringApplicationBuilder) configure} é sobrescrito para indicar à aplicação Spring
 * Boot qual classe principal (normalmente a classe {@link AplicacaoApplication}) deve ser usada para inicializar a
 * aplicação quando implantada em um contêiner de servlet como Tomcat, Jetty, etc.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 *     @Override
 *     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
 *         return application.sources(AplicacaoApplication.class);
 *     }
 * }</pre>
 *
 * @author [João Cleber Dias Maciel]
 * @see SpringBootServletInitializer
 * @see SpringApplicationBuilder
 * @see AplicacaoApplication
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configura a aplicação Spring Boot para ser inicializada corretamente em um ambiente de servlet.
     *
     * @param application O construtor de aplicação Spring utilizado para configurar a aplicação.
     * @return O construtor de aplicação configurado com a classe principal da aplicação.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AplicacaoApplication.class);
    }
}
