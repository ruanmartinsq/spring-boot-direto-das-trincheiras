package academy.devdojo.config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties configurationProperties;
    //carregamento deles é quando roda a aplicação

    //estamos rodando o default e nele nao temos url, username e password (application.yml)

    @Bean
    @Primary
    //@Profile("mysql") //se profile for isso executa embaixo
    public Connection connectionMySql() {
        return new Connection(configurationProperties.url(),
                configurationProperties.username(),
                configurationProperties.password());
    }

    @Bean(name = "connectionMongoDB") //passa um nome especifico
    @Profile("mongo") //se profile for isso executa em baixo
    //bean so é criado se o objeto for criado (bean objeto gerenciado pelo spring)
    public Connection connectionMongo() {
        return new Connection(configurationProperties.url(),
                configurationProperties.username(),
                configurationProperties.password());
    }

}
