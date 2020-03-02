package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("datasourceURLProperty")
@Scope("prototype")
public class DatasourceURLPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DatasourceURLPropertyImpl(String value) {
        this.name = "datasource.url";
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
