package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
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

    //@Order(1) ordem pra ser executado
    @Test
    @DisplayName("findAll returns a list with all producerList")
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
}