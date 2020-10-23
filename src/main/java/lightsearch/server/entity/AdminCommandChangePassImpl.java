package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandChangePass")
@Scope("prototype")
public class AdminCommandChangePassImpl implements AdminCommand {

    private final String password;

    public AdminCommandChangePassImpl(String password) {
        this.password = password;
    }

    @Override
    public String name() {
        return AdminCommands.CHANGE_PASS;
    }

    public String password() {
        return password;
    }
}
