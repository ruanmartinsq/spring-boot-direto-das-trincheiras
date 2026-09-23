package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean //mocka so uma "parte"
    private AnimeHardCodedRepository repository;
    private List<Anime> animeList;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var bleach = Anime.builder().id(1L).name("Bleach").build();
        var aot = Anime.builder().id(2L).name("Atack on Titan").build();
        var sao = Anime.builder().id(3L).name("Sword Art Online").build();
        animeList = new ArrayList<>(List.of(bleach, aot, sao));
    }

    @Test
    @DisplayName("GET v1/animes returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnime_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var response = readResourceFile("anime/get-anime-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/animes?name=Bleach returns list with found object when name exists")
    void findAll_returnsFoundAnime_WhenProducerIsFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-anime-bleach-name-200.json");
        var name = "Bleach";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.content()).json(response));
    }

    @Test
    @DisplayName("GET v1/animes?name=x returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-anime-x-name-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/anime/1 returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var response = readResourceFile("anime/get-anime-by-id-name-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("GET v1/anime/99 throws ResponseStatusException 404 when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime nao encontrado"));
    }

    @Test
    @DisplayName("POST v1/animes creates an anime")
    @Order(6)
    void save_CreatesAnime_WhenSucessful() throws Exception {
        var animeToSave = Anime.builder().id(99L).name("My Hero Academy").build();
        var response = readResourceFile("anime/post-response-anime-201.json");
        var request = readResourceFile("anime/post-request-anime-200.json");

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("DELETE v1/animes/1 removes a anime")
    @Order(7)
    void delete_RemovesAnime_WhenSucessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/99 throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime nao encontrado"));
    }

    @Test
    @DisplayName("PUT v1/animes updates an anime")
    @Order(9)
    void update_UpdatesAnime_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/put-request-anime-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("PUT v1/animes throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/put-request-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime nao encontrado"));
    }


    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName));
        return new String(Files.readAllBytes(file.getFile().toPath()));
    }

}