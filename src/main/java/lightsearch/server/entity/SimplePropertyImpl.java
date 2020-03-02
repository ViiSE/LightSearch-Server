package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("simpleProperty")
@Scope("prototype")
public class SimplePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public SimplePropertyImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        return name + "=" + value;
    }
}
