package academy.devdojo.request;

//pode ser DT0 também

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//nao precisa do builder pq geralmente ja passam valor pra ele por parametro
public class ProducerPostRequest {
    private String name;
}
