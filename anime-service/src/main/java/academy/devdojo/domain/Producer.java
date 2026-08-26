package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {
    @EqualsAndHashCode.Include
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    @JsonProperty("name") //valor ao passar uma requisicao
    private String name;
    private LocalDateTime createdAt;

}
