package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandClientTimeout")
@Scope("prototype")
public class AdminCommandClientTimeoutImpl implements AdminCommand {

    private final int timeout;

    public AdminCommandClientTimeoutImpl(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String name() {
        return AdminCommands.CHANGE_CLIENT_TIMEOUT;
    }

    public int timeout() {
        return timeout;
    }
}
