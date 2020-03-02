package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("databaseNameProperty")
@Scope("prototype")
public class DatabaseNamePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DatabaseNamePropertyImpl(String value) {
        this.name = "dbName";
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
