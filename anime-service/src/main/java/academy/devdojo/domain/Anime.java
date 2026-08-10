package academy.devdojo.domain;

import lombok.Getter;

import java.util.List;

@Getter //apenas adiciona os getters do id e name
public class Anime {
    private Long id; //quando trabalha com ID geralmente usa Wrapper (Long)
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        var naruto = new Anime(1L, "Naruto");
        var deathNote = new Anime(2L, "Death Note");
        var onePiece = new Anime(3L, "One Piece");

        return List.of(naruto, deathNote, onePiece);
    }
}
