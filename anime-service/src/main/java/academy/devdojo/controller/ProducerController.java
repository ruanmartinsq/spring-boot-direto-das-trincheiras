package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/producer") //se colocar v1/producers/ tem que cuidar/tratar dele na segurança tb
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> listarProducers(@RequestParam(required = false) String name) {
        log.debug("Request reveived to list all producers, param name '{}'", name);
        var producers = Producer.getProducers();
        var producersGetResponseList = MAPPER.toProducerGetResponseList(producers);

        if (name == null) return ResponseEntity.ok(producersGetResponseList);

        var response = producersGetResponseList.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find producer by id: '{}'", id);

        var producerGetResponse = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .map(MAPPER::toProducerGetResponse) //producer -> MAPPER.toProducerGetResponse(producer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not Found"));

        return ResponseEntity.ok(producerGetResponse);
    }

    //não é Idempotente
    //quando vc executa varias vezes da o mesmo resultado/retorno -> idempotente
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) { //Spring, pegue os headers que vieram na requisição e coloque eles nessa variável headers."
        log.info("{}, headers");

        var producer = MAPPER.toProducer(producerPostRequest);
        var response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer); //add no list
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

        //return ResponseEntity.noContent().build(); //fez com sucesso mas nao tem necessidade de retornar um conteudo
        //return ResponseEntity.ok(producer);
        //return ResponseEntity.status(HttpStatus.CREATED).body(producer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);
        var producerToDelete = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not Found"));

        Producer.getProducers().remove(producerToDelete);
        return ResponseEntity.noContent().build();
    }
}
