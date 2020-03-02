package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandAddBlacklist")
@Scope("prototype")
public class AdminCommandAddBlacklistImpl implements AdminCommand {

    private final String IMEI;

    public AdminCommandAddBlacklistImpl(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public String name() {
        return AdminCommands.ADD_BLACKLIST;
    }

    public String IMEI() {
        return IMEI;
    }
}
