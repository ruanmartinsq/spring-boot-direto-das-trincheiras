package external.dependency;

import lombok.AllArgsConstructor;
import lombok.ToString;

//essa classe está fora do academy devdojo, ent o component scan, nao escaneia ela
@ToString
@AllArgsConstructor
public class Connection {
    private String host;
    private String username;
    private String password;
}
