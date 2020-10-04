package springboottesting.springframework.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Primary
@Component
@Profile("property")
public class PropertyNumberProducer implements NumberProducer {
    private final int numberProperty;

    public PropertyNumberProducer(@Value("${property.number}") int numberProperty) {
        this.numberProperty = numberProperty;
    }

    @Override
    public int produceNumber() {
        return numberProperty;
    }
}
