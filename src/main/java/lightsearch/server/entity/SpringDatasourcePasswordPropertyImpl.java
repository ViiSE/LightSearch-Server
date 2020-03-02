package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("springDatasourcePasswordProperty")
@Scope("prototype")
public class SpringDatasourcePasswordPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public SpringDatasourcePasswordPropertyImpl(String value) {
        this.name = "spring.datasource.password";
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
