package tech.hephaestusforge.messaging.antenna.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApplicationProperty {
    @Value("${persistence-unit.name}")
    private String name;

    @Value("${persistence-unit.description}")
    private String description;

    @Value("${persistence-unit.provider}")
    private String provider;

    @Value("${persistence-unit.driver-class-name}")
    private String driverClassName;

    @Value("${persistence-unit.url}")
    private String url;

    @Value("${persistence-unit.username}")
    private String username;

    @Value("${persistence-unit.password}")
    private String password;
}
