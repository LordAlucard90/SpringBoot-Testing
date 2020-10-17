package springboottesting.springframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/examples")
public class ExampleController {
    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping
    public List<ExampleEntity> getAllExamples() {
        return exampleService.getAllExamples();
    }

    @GetMapping("{exampleId}")
    public ExampleEntity getExample(@PathVariable Long exampleId) {
        return exampleService.getExample(exampleId);
    }
}
