package pmk.artamonova.rgr.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class LiquieBaseConfig {

    private final DataSource dataSource;

    private final DataBaseProperties properties;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(properties.getChangelogFile());
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
