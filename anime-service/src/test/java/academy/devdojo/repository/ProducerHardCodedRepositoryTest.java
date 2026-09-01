package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("findAll returns a list with all producerList")
    void findAll_ReturnsAllProducers_Whensuccesfull() {
        //Quando vc receber/sentir um injectmock e algm chamou o producer data.geproducers eu qro que vc retorne os producerList
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findAll();

        Assertions.assertThat(producers)
                .isNotNull()
                .hasSize(producerList.size());
    }
}