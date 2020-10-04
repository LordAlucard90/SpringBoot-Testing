package springboottesting.springframework.context;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"even", "component-scan"})
@Component
@Primary
public class EvenNumberProducer implements NumberProducer {
    @Override
    public int produceNumber() {
        return 2;
    }
}
