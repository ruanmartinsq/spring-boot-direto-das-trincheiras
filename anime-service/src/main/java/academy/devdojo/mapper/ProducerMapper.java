package academy.devdojo.mapper;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

//o mapper ele pega um obj e tenta fazer o mapeamento para o target
@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest postRequest);

    //@Mapping(source = "createdAt", target = "createdAt") //dizendo ao MapStruct de onde pegar o valor de createdAt e para onde colocar.
    //source = "createdAt" → o valor vem do parâmetro createdAt do metodo.
    //target = "createdAt" → esse valor será colocado no atributo createdAt do Producer.
    //Producer producer = new Producer();
    //
    //producer.setId(request.getId());
    //producer.setName(request.getName());
    //producer.setCreatedAt(createdAt);
    Producer toProducer(ProducerPutRequest request);

    ProducerGetResponse toProducerGetResponse(Producer producer);
    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);


    ProducerPostResponse toProducerPostResponse(Producer producerSaved);
}



