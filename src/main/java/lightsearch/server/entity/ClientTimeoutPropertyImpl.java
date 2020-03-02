package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientTimeoutProperty")
@Scope("prototype")
public class ClientTimeoutPropertyImpl implements Property<String> {

    private final String name;
    private final int value;

    public ClientTimeoutPropertyImpl(int value) {
        this.name = "lightsearch.server.settings.timeout.client-timeout";
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
