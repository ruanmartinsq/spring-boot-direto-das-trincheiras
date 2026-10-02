package academy.devdojo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//essa classe está fora do academy devdojo, ent o component scan, nao escaneia ela
@ToString
@AllArgsConstructor
@Getter
public class Connection {
    private String host;
    private String username;
    private String password;
}
