package academy.devdojo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/greetings")
//@RequestMapping("greetings")
@Slf4j // cria o objeto "log"
public class HelloController {

    @GetMapping()
     public String hi() {
         return "Hello world";
     }

    @PostMapping
    public Long save (@RequestBody String name) {
        log.info("save '{}'", name);
        return ThreadLocalRandom.current().nextLong(1, 1000); //Pega um gerador de números aleatórios adequado para uso em aplicações com múltiplas threads.

     }
}
