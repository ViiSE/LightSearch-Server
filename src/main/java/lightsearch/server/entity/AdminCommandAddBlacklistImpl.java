package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminCommandAddBlacklist")
@Scope("prototype")
public class AdminCommandAddBlacklistImpl implements AdminCommand {

    private final List<String> IMEIList;

    public AdminCommandAddBlacklistImpl(List<String> IMEIList) {
        this.IMEIList = IMEIList;
    }

    @Override
    public String name() {
        return AdminCommands.ADD_BLACKLIST;
    }

    public List<String> IMEIList() {
        return IMEIList;
    }
}
