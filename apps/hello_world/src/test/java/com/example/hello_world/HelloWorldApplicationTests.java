package com.example.hello_world;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        // Vérifie que l'application démarre correctement
        assertThat(context).isNotNull();
    }

    @Test
    void controllerExists() {
        // Vérifie que le HelloController est bien chargé par Spring
        assertThat(context.getBean(HelloController.class)).isNotNull();
    }
}