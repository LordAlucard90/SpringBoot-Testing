package springboottesting.springframework.context;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"odd", "component-scan"})
@Component
public class OddNumberProducer implements NumberProducer {
    @Override
    public int produceNumber() {
        return 1;
    }
}
