package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("driverClassNameProperty")
@Scope("prototype")
public class DriverClassNamePropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public DriverClassNamePropertyImpl(String value) {
        this.name = DatasourceConstants.DRIVER_CLASS_NAME;
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
