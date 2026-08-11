package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/animes") //se colocar v1/animes/ tem que cuidar/tratar dele na segurança tb
@Slf4j
public class AnimeController {

    @GetMapping
    public List<Anime> listarAnimes(@RequestParam (required = false) String name) {
        var animes = Anime.getAnimes();
        if (name == null) return animes;

        return animes.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
        return Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst().orElse(null);
    }

    //não é Idempotente
    //quando vc executa varias vezes da o mesmo resultado/retorno -> idempotente
    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(100_000));
        Anime.getAnimes().add(anime);
        return anime;
    }
}
