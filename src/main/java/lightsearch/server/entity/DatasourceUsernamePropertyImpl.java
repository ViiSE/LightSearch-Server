package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("datasourceUsernameProperty")
@Scope("prototype")
public class DatasourceUsernamePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DatasourceUsernamePropertyImpl(String value) {
        this.name = "datasource.username";
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
