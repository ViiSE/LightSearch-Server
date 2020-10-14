package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientTimeoutPropertyValue")
@Scope("prototype")
public class ClientTimeoutPropertyValueImpl implements Property<Integer> {

    private final String name;
    private final int value;

    public ClientTimeoutPropertyValueImpl(int value) {
        this.name = "lightsearch.server.jwt-valid-day-count";
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Integer as() {
        return value;
    }
}
