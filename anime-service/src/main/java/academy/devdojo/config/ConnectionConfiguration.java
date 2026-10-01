package academy.devdojo.config;
import external.dependency.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class ConnectionConfiguration {
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    //carregamento deles é quando roda a aplicação

    //estamos rodando o default e nele nao temos url, username e password (application.yml)

    @Bean
    @Primary
    @Profile("mysql") //se profile for isso executa embaixo
    public Connection connectionMySql() {
        return new Connection(url, username, password);
    }

    @Bean(name = "connectionMongoDB") //passa um nome especifico
    @Profile("mongo") //se profile for isso executa em baixo
    //bean so é criado se o objeto for criado (bean objeto gerenciado pelo spring)
    public Connection connectionMongo() {
        return new Connection(url, username, password);
    }

}
