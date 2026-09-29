package academy.devdojo.commons;

import academy.devdojo.domain.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {

    public List<Producer> newProducerList() {
        var dateTime = "2026-09-16T16:48:22.941916325";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(localDateTime).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(localDateTime).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(localDateTime).build();
        return new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    public Producer newProducertoSave() {
        return Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();
    }
}
