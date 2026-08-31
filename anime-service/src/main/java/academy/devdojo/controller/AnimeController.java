package academy.devdojo.controller;

import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //colocar ("v1") -> estará definindo o nome do Bean (o nome do componente)
@RequestMapping("v1/animes") //se colocar v1/animes/ tem que cuidar/tratar dele na segurança tb
@Slf4j
@Controller
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeMapper mapper;
    private final AnimeService service;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listarAnimes(@RequestParam(required = false) String name) {
        log.debug("Request reveived to list all animes, param name '{}'", name);
        var animes = service.findAll(name);
        var animeGetResponse = mapper.toAnimeGetResponseList(animes);
        return ResponseEntity.ok(animeGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var animeId = service.findByIdOrTrhowNotFound(id);
        AnimeGetResponse animeGetResponse = mapper.toAnimeGetResponse(animeId);
        return ResponseEntity.ok(animeGetResponse);
    }

    //não é Idempotente
    //quando vc executa varias vezes da o mesmo resultado/retorno -> idempotente
    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime: '{}'", request);
        var anime = mapper.toAnime(request);
        var animeToSave = service.save(anime);
        var animePostResponse = mapper.toAnimePostResponse(animeToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(animePostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping() //conflito com mais uma opcao de endpoint
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to updtae anime by anime: {}", request);
        var animeToUpdate = mapper.toAnime(request);
        service.update(animeToUpdate);
        return ResponseEntity.ok().build();
    }
}
