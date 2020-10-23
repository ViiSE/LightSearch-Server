package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminPasswordProperty")
@Scope("prototype")
public class AdminPasswordPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public AdminPasswordPropertyImpl(String value) {
        this.name = "lightsearch.server.admin.password";
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
