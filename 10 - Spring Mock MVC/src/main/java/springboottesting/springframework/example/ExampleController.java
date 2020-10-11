package springboottesting.springframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ExampleController {
    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @RequestMapping(value = { "/examples"})
    public String getAllExamples(Map<String, Object> model) {
        model.put("examples", exampleService.getAllExamples());
        return "examples/examplesList";
    }

    @RequestMapping(value = { "/example"})
    public String getExample(Long id, Map<String, Object> model) {
        model.put("example", exampleService.getExample(id));
        return "examples/exampleView";
    }
}
