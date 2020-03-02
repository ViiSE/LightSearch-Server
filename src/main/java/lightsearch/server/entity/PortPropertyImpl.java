package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("portProperty")
@Scope("prototype")
public class PortPropertyImpl implements Property<String> {

    private final String name;
    private final int value;

    public PortPropertyImpl(int value) {
        this.name = "port";
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        return String.valueOf(value);
    }
}
