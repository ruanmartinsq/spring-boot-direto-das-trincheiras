package academy.devdojo.request;


import lombok.*;


@Getter
@Setter
@Builder
@ToString
public class AnimePutRequest {
    private Long id;
    private String name;
}
