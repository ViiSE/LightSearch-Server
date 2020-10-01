package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminCommandDelBlacklistList")
@Scope("prototype")
public class AdminCommandDelBlacklistListImpl implements AdminCommand {

    private final List<String> IMEIList;

    public AdminCommandDelBlacklistListImpl(List<String> IMEIList) {
        this.IMEIList = IMEIList;
    }

    @Override
    public String name() {
        return AdminCommands.DEL_BLACKLIST;
    }

    public List<String> IMEI() {
        return IMEIList;
    }
}
