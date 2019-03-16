package microservices.web_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.core.SpringVersion;


@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {


        System.out.println("version: " + SpringVersion.getVersion());

        SpringApplication.run(WebApplication.class, args);
    }

}
