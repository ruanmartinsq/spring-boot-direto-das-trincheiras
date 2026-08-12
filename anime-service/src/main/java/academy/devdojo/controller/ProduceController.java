package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/producer") //se colocar v1/producers/ tem que cuidar/tratar dele na segurança tb
@Slf4j
public class ProduceController {

    @GetMapping
    public List<Producer> listarProducers(@RequestParam (required = false) String name) {
        var producers = Producer.getProducers();
        if (name == null) return producers;

        return producers.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable Long id) {
        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst().orElse(null);
    }

    //não é Idempotente
    //quando vc executa varias vezes da o mesmo resultado/retorno -> idempotente
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
    headers = "x-api-key")
    public ResponseEntity<Producer> save(@RequestBody Producer producer, @RequestHeader HttpHeaders headers) { //Spring, pegue os headers que vieram na requisição e coloque eles nessa variável headers."
        log.info("{}, headers");
        producer.setId(ThreadLocalRandom.current().nextLong(100_000));
        Producer.getProducers().add(producer);

        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", "My Key");

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(producer);
        //return ResponseEntity.noContent().build(); //fez com sucesso mas nao tem necessidade de retornar um conteudo
        //return ResponseEntity.ok(producer);
        //return ResponseEntity.status(HttpStatus.CREATED).body(producer);
    }
}
