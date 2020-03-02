package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("datasourceDriverClassNameProperty")
@Scope("prototype")
public class DatasourceDriverClassNamePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DatasourceDriverClassNamePropertyImpl(String value) {
        this.name = "datasource.driver-class-name";
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
