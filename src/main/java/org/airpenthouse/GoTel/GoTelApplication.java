package org.airpenthouse.GoTel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class GoTelApplication {

    public static void main(String[] args) {
        ApplicationContext xo = SpringApplication.run(GoTelApplication.class, args);

    }

}
