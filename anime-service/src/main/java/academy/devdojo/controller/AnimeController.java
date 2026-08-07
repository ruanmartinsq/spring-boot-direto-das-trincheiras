package academy.devdojo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/animes") //se colocar v1/animes/ tem que cuidar/tratar dele na segurança tb
@Slf4j
public class AnimeController {

    @GetMapping
    public List<String> listarAnimes() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return List.of("One piece", "Death Note", "Naruto");
    }
}
