package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    @Getter
    private final List<Anime> animes = new ArrayList<>();

    {
        var naruto = Anime.builder().id(1L).name("naruto").build();
        var deathNote = Anime.builder().id(2L).name("Death Note").build();
        var onePiece = Anime.builder().id(3L).name("One Piece").build();
        animes.addAll(List.of(naruto, deathNote, onePiece));
    }
}
