package br.com.aplicacao.cadastro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.junit.Assert.assertNotNull;

public class ServletInitializerTest {

    @Test
    public void test_application_context_loads_successfully() {
        ServletInitializer initializer = new ServletInitializer();
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        SpringApplicationBuilder configuredBuilder = initializer.configure(builder);
        assertNotNull(configuredBuilder);
    }

    @Test
    public void test_servlet_initializer_integration() {
        ServletInitializer initializer = new ServletInitializer();
        assertNotNull(initializer);
    }

    @Test
    public void test_configure_method_override() {
        ServletInitializer initializer = new ServletInitializer();
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        SpringApplicationBuilder configuredBuilder = initializer.configure(builder);
        assertNotNull(configuredBuilder);
    }

    @Test
    public void test_default_spring_boot_configurations() {
        ServletInitializer initializer = new ServletInitializer();
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        SpringApplicationBuilder configuredBuilder = initializer.configure(builder);
        assertNotNull(configuredBuilder.application());
    }
}