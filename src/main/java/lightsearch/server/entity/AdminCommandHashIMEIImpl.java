package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandHashIMEI")
@Scope("prototype")
public class AdminCommandHashIMEIImpl implements AdminCommand {

    private final String IMEI;

    public AdminCommandHashIMEIImpl(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public String name() {
        return AdminCommands.HASH_IMEI;
    }

    public String IMEI() {
        return IMEI;
    }
}
