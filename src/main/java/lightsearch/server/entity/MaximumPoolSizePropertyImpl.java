package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("maximumPoolSizeProperty")
@Scope("prototype")
public class MaximumPoolSizePropertyImpl implements Property<Long> {

    private final String name;
    private final long value;

    public MaximumPoolSizePropertyImpl(long value) {
        this.name = DatasourceConstants.MAXIMUM_POOL_SIZE;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Long as() {
        return value;
    }
}
