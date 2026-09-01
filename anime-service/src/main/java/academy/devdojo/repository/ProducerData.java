package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerData {
    private final List<Producer> producers = new ArrayList<>();

    {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimattion = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madHouse = Producer.builder().id(3L).name("Mad House").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, kyotoAnimattion, madHouse));
    }

    public List<Producer> getProducers() {
        return producers;
    }
}
