package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandKick")
@Scope("prototype")
public class AdminCommandKickImpl implements AdminCommand {

    private final String IMEI;

    public AdminCommandKickImpl(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public String name() {
        return AdminCommands.KICK;
    }

    public String IMEI() {
        return IMEI;
    }
}
