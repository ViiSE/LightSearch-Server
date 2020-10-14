package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dbTypeProperty")
@Scope("prototype")
public class DbTypePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DbTypePropertyImpl(String value) {
        this.name = DatasourceConstants.DB_TYPE;
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
