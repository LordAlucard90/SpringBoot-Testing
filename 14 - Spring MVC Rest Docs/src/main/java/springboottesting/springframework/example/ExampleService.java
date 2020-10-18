package springboottesting.springframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private final ExampleRepository exampleRepository;

    public ExampleService(@Autowired ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public ExampleEntity getExample(Long id) {
        return exampleRepository.findById(id).orElse(new ExampleEntity());
    }

    public ExampleEntity saveExample(ExampleEntity entity) {
        return exampleRepository.save(entity);
    }
}
