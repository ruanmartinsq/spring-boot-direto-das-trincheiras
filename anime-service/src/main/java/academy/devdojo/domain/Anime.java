package academy.devdojo.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter //apenas adiciona os getters do id e name
@Setter
@Builder
@AllArgsConstructor//gera automaticamente um construtor com um parâmetro para cada atributo presente na classe.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    private String name;

    //@AllArgsConstructor faz isso
    //public Anime(Long id, String name) {
        //this.id = id;
        //this.name = name;
    //}
}
