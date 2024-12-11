package com.cadastro.aplicacao;

import br.com.aplicacao.cadastro.AplicacaoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.Assert.assertThrows;

class AplicacaoApplicationTests {

    @Test
    public void test_application_fails_to_start_due_to_missing_configuration() {
        System.setProperty("spring.config.location", "nonexistent.yml");
        assertThrows(Exception.class, () -> SpringApplication.run(AplicacaoApplication.class));
    }

    @Test
    public void test_invalid_command_line_arguments_cause_application_to_crash() {
        String[] args = {"--invalid.argument"};
        assertThrows(Exception.class, () -> AplicacaoApplication.main(args));
    }

    @Test
    public void test_spring_boot_application_context_fails_to_load() {
        System.setProperty("spring.config.location", "nonexistent.yml");
        assertThrows(Exception.class, () -> SpringApplication.run(AplicacaoApplication.class));
    }
}
