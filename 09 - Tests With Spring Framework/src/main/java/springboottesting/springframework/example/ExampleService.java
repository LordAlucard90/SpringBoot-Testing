package springboottesting.springframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleService {
    private final ExampleRepository exampleRepository;

    public ExampleService(@Autowired ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public List<ExampleEntity> getAllExamples() {
        return exampleRepository.findAll();
    }
}
