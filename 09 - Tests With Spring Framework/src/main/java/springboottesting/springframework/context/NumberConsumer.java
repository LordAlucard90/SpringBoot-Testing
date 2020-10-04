package springboottesting.springframework.context;

import org.springframework.stereotype.Service;

@Service
public class NumberConsumer {
    private final NumberProducer numberProducer;

    public NumberConsumer(NumberProducer numberProducer) {
        this.numberProducer = numberProducer;
    }

    public int consumeNumber() {
        return numberProducer.produceNumber();
    }
}
