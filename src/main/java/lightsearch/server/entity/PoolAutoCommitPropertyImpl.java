package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("poolAutoCommitProperty")
@Scope("prototype")
public class PoolAutoCommitPropertyImpl implements Property<Boolean> {

    private final String name;
    private final boolean value;

    public PoolAutoCommitPropertyImpl(boolean value) {
        this.name = DatasourceConstants.POOL_AUTO_COMMIT;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Boolean as() {
        return value;
    }
}
