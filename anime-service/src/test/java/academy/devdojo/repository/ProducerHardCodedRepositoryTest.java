package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {
    @InjectMocks
    private ProducerHardCodedRepository repository;
    private final List<Producer> producerList = new ArrayList<>();

    @Mock
    private ProducerData producerData;

    //sempre executado antes de todos os testes, cada metodo executado (10 testes, executado 10 vezes)
    @BeforeEach
    void init() {
        {
            var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(LocalDateTime.now()).build();
            var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
            var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
            producerList.addAll(List.of(ufotable, witStudio, studioGhibli));
        }
    }

    @Test
    @DisplayName("findAll returns a list with all producerList")
    @Order(1)
    void findAll_ReturnsAllProducers_Whensuccesfull() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        //Mockito, quando alguém chamar getProducers() nesse producerData falso, faça ele retornar producerList
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findAll();

        Assertions.assertThat(producers)
                .isNotNull()
                .hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(2)
    void findById_ReturnsAllProducersById_Whensuccesfull() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        //Mockito, quando alguém chamar getProducers() nesse producerData falso, faça ele retornar producerList
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();
        var producers = repository.findById(expectedProducer.getId());

        Assertions.assertThat(producers)
                .isPresent()
                .contains(expectedProducer);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        //Mockito, quando alguém chamar getProducers() nesse producerData falso, faça ele retornar producerList
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findByName(null);
        Assertions.assertThat(producers)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found object when name exists")
    @Order(4)
    void findByName_ReturnsFoundProducer_WhenNameIsFound() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        //Mockito, quando alguém chamar getProducers() nesse producerData falso, faça ele retornar producerList
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());

        Assertions.assertThat(producers)
                .isNotNull()
                .contains(expectedProducer);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToSave = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();
        var producer = repository.save(producerToSave);

        Assertions.assertThat(producer)
                .isEqualTo(producerToSave)
                .hasNoNullFieldsOrProperties();

        Optional<Producer> producerSavedOptional = repository.findById(producerToSave.getId());

        Assertions.assertThat(producerSavedOptional)
                .isPresent()
                .contains(producerToSave);
    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(6)
    void delete_RemovePorducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToDelete = producerList.getFirst();
        repository.delete(producerToDelete);

        Assertions.assertThat(this.producerList)
                .doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(7)
    void update_UpdatesProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Aniplex");
        repository.update(producerToUpdate);

        Assertions.assertThat(this.producerList)
                .contains(producerToUpdate);

        Optional<Producer> producerUpdatedOptional = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo(producerToUpdate.getName());
    }
}