package academy.devdojo.response;

//pode ser DT0 também

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProducerPostResponse {
    private Long id;
    private String name;

}
