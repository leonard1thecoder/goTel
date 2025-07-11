package org.airpenthouse.GoTel;

import org.springframework.boot.SpringApplication;

public class TestGoTelApplication {

    public static void main(String[] args) {
        SpringApplication.from(GoTelApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
