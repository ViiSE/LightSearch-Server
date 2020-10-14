package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("poolMaxLifeTimeProperty")
@Scope("prototype")
public class PoolMaxLifeTimePropertyImpl implements Property<Long> {

    private final String name;
    private final long value;

    public PoolMaxLifeTimePropertyImpl(long value) {
        this.name = DatasourceConstants.POOL_MAX_LIFE_TIME;
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
