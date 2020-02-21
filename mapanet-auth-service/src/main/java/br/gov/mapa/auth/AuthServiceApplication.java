package br.gov.mapa.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.gov.mapa.core.config.JwtConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"br.gov.mapa.core.model"})
@EnableJpaRepositories({"br.gov.mapa.core.repository"})
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("br.gov.mapa")
public class AuthServiceApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
