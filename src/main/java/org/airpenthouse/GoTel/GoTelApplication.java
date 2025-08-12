package org.airpenthouse.GoTel;

import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class GoTelApplication {

    public static void main(String[] args) {
        Log.info("Application Name : " + PropertiesUtilManager.getPropertiesValue("spring.application.name") + "Started");
        SpringApplication.run(GoTelApplication.class, args);
    }

}
