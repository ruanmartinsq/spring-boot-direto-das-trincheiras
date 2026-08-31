package academy.devdojo.config;
import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConnectionConfiguration {
    @Bean //se vc precisar de criar um obj do tipo connection, vc vem nesse bean e usa esse metodo para injetar no connection
    @Primary //se tiver 2 usa esse como padrao
    public Connection connectionMySql() {
        return new Connection("localhost", "devdojoMySql", "ruan");
    }

    @Bean(name = "connectionMongoDB") //passa um nome especifico
    public Connection connectionMongo() {
        return new Connection("localhost", "devdojoMongo", "ruan");
    }

}
