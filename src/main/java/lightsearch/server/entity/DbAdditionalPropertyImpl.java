package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dbAdditionalProperty")
@Scope("prototype")
public class DbAdditionalPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DbAdditionalPropertyImpl(String value) {
        this.name = DatasourceConstants.ADDITIONAL;
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
