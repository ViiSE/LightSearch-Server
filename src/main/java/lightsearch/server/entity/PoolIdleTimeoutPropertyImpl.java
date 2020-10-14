package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("poolIdleTimeoutProperty")
@Scope("prototype")
public class PoolIdleTimeoutPropertyImpl implements Property<Long> {

    private final String name;
    private final long value;

    public PoolIdleTimeoutPropertyImpl(long value) {
        this.name = DatasourceConstants.POOL_IDLE_TIMEOUT;
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
