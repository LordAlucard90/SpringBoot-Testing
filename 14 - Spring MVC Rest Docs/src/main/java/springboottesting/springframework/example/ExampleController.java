package springboottesting.springframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/examples")
public class ExampleController {
    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @PostMapping
    public ExampleEntity crateAnExample(@RequestBody @Validated ExampleEntity exampleEntity) {
        return exampleService.saveExample(exampleEntity);
    }

    @GetMapping("{exampleId}")
    public ExampleEntity getExample(@PathVariable Long exampleId) {
        return exampleService.getExample(exampleId);
    }
}
