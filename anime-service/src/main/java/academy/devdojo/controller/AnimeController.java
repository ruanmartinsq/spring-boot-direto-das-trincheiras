package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/animes") //se colocar v1/animes/ tem que cuidar/tratar dele na segurança tb
public class AnimeController {

    @GetMapping
    public List<String> listarAnimes() {
        return List.of("One piece", "Death Note", "Naruto");
    }
}
