package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("springDatasourceUsernameProperty")
@Scope("prototype")
public class SpringDatasourceUsernamePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public SpringDatasourceUsernamePropertyImpl(String value) {
        this.name = "spring.datasource.username";
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
