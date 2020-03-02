package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ipProperty")
@Scope("prototype")
public class IpPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public IpPropertyImpl(String value) {
        this.name = "ip";
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        return value;
    }
}
