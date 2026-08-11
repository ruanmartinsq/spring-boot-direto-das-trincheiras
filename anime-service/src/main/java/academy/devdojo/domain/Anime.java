package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter //apenas adiciona os getters do id e name
@Setter
@AllArgsConstructor //gera automaticamente um construtor com um parâmetro para cada atributo presente na classe.
public class Anime {
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        var naruto = new Anime(1L, "Naruto");
        var deathNote = new Anime(2L, "Death Note");
        var onePiece = new Anime(3L, "One Piece");
        animes.addAll(List.of(naruto, deathNote, onePiece));
    }

    //@AllArgsConstructor faz isso
    //public Anime(Long id, String name) {
        //this.id = id;
        //this.name = name;
    //}


}
