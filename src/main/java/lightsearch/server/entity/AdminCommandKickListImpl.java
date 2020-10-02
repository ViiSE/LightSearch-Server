package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminCommandKickList")
@Scope("prototype")
public class AdminCommandKickListImpl implements AdminCommand {

    private final List<String> IMEIList;

    public AdminCommandKickListImpl(List<String> IMEIList) {
        this.IMEIList = IMEIList;
    }

    @Override
    public String name() {
        return AdminCommands.KICK;
    }

    public List<String> IMEI() {
        return IMEIList;
    }
}
