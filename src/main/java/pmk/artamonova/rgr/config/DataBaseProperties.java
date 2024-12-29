package pmk.artamonova.rgr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:database.properties")
@Getter
@Setter
public class DataBaseProperties {

    private String connectionUrl;

    private String username;

    private String password;

    private String driver;

    private String changelogFile;

}
