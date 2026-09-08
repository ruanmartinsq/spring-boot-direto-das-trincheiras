package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;

    //mocka tudo aquilo que o producerService chama, todos os atributos de classe
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producerList;

    @BeforeEach
    void init() {
        {
            var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(LocalDateTime.now()).build();
            var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
            var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
            producerList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
        }
    }

    @Test
    @DisplayName("findAll returns a list with all producerList when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        //Mockito, quando alguém chamar getProducers() nesse producerData falso, faça ele retornar producerList
        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);

        Assertions.assertThat(producers)
                .isNotNull()
                .hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundProducer_WhenNameIsFound() {
        var producer = producerList.getFirst();
        var expectedProducerFound = singletonList(producer);
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducerFound);

        var producersFound = service.findAll(producer.getName());

        Assertions.assertThat(producersFound)
                .containsAll(expectedProducerFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        var name = "not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());

        var producers = repository.findByName(name);
        Assertions.assertThat(producers)
                .isNotNull()
                .isEmpty();
    }
}