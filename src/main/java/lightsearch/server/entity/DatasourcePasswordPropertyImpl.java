package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("datasourcePasswordProperty")
@Scope("prototype")
public class DatasourcePasswordPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DatasourcePasswordPropertyImpl(String value) {
        this.name = "datasource.password";
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
