package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter //apenas adiciona os getters do id e name
@Setter
@AllArgsConstructor//gera automaticamente um construtor com um parâmetro para cada atributo presente na classe.
public class Producer {
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    @JsonProperty("name") //valor ao passar uma requisicao
    private String name;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = new Producer(1L, "Mappa");
        var kyotoAnimattion = new Producer(2L, "Kyoto Animattion");
        var madHouse = new Producer(3L, "Mad house");
        producers.addAll(List.of(mappa, kyotoAnimattion, madHouse));
    }

    //@AllArgsConstructor faz isso
    //public Anime(Long id, String name) {
        //this.id = id;
        //this.name = name;
    //}


}
