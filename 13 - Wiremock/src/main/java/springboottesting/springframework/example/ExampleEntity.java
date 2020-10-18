package springboottesting.springframework.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleEntity {
    private Long id;
    private String exampleString;
}
