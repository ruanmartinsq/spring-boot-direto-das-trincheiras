package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.class)
class AnimeHardCodedRepositoryTest {
    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;
    private List<Anime> animeList;

    @BeforeEach
    void init() {
        var bleach = Anime.builder().id(1L).name("Bleach").build();
        var aot = Anime.builder().id(2L).name("Atack on Titan").build();
        var sao = Anime.builder().id(3L).name("Sword Art Online").build();
        animeList = new ArrayList<>(List.of(bleach, aot, sao));
    }

    @Test
    @DisplayName("findAll returns a list with all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhensSucesfull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var anime = repository.findAll();
        Assertions.assertThat(anime)
                .isNotNull()
                .hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findById returns a anime with given id")
    @Order(2)
    void findById_ReturnsAnimeById_WhenSucessfull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var expectedAnime = animeList.getFirst();
        var anime = repository.findById(expectedAnime.getId());
        Assertions.assertThat(anime)
                .isPresent()
                .contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName returns a empty list with given name")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var anime = repository.findByName(null);
        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(4)
    void save_CreatesAnime_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animeToSave = Anime.builder()
                .id(99L)
                .name("Skate Park")
                .build();

        Anime anime = repository.save(animeToSave);

        Assertions.assertThat(anime)
                .isEqualTo(animeToSave)
                .hasNoNullFieldsOrProperties();

        Optional<Anime> animeSavedOptional = repository.findById(anime.getId());
        Assertions.assertThat(animeSavedOptional)
                .isPresent()
                .contains(animeToSave);
    }

    @Test
    @DisplayName("delete removes an anime")
    @Order(5)
    void delete_RemovesAnime_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToRemove = animeList.getFirst();
        repository.delete(animeToRemove);

        Assertions.assertThat(animeList)
                .doesNotContain(animeToRemove);
    }

    @Test
    @DisplayName("update updates an anime")
    @Order(5)
    void update_UpdatesAnime_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Hellsing");

        repository.update(animeToUpdate);

        Assertions.assertThat(animeList)
                .contains(animeToUpdate);

        var animeUpdatedOptional = repository.findById(animeToUpdate.getId());
        Assertions.assertThat(animeUpdatedOptional.get().getName()).isEqualTo(animeToUpdate.getName());
    }
}