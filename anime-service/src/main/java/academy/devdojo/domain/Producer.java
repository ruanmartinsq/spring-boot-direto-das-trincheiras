package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Producer {
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    @JsonProperty("name") //valor ao passar uma requisicao
    private String name;
    private LocalDateTime createdAt;

}
