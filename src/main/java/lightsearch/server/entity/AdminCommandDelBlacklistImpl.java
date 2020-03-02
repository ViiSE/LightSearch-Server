package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandDelBlacklist")
@Scope("prototype")
public class AdminCommandDelBlacklistImpl implements AdminCommand {

    private final String IMEI;

    public AdminCommandDelBlacklistImpl(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public String name() {
        return AdminCommands.DEL_BLACKLIST;
    }

    public String IMEI() {
        return IMEI;
    }
}
