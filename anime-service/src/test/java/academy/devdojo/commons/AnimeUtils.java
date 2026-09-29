package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList() {
        var bleach = Anime.builder().id(1L).name("Bleach").build();
        var aot = Anime.builder().id(2L).name("Atack on Titan").build();
        var sao = Anime.builder().id(3L).name("Sword Art Online").build();
        return new ArrayList<>(List.of(bleach, aot, sao));
    }

    public Anime newAnimeSave() {
        return Anime.builder().id(99L).name("My Hero Academy").build();
    }
}
