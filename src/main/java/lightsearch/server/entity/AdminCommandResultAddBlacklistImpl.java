package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandAddBlacklistResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandResultAddBlacklist")
@Scope("prototype")
public class AdminCommandResultAddBlacklistImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final String hashIMEI;

    public AdminCommandResultAddBlacklistImpl(AdminCommandResult admCmdRes, String hashIMEI) {
        this.admCmdRes = admCmdRes;
        this.hashIMEI = hashIMEI;
    }

    @Override
    public Object formForSend() {
        return new AdminCommandAddBlacklistResultDTO(admCmdRes.isDone(), admCmdRes.message(), hashIMEI);
    }

    @Override
    public boolean isDone() {
        return admCmdRes.isDone();
    }

    @Override
    public String message() {
        return admCmdRes.message();
    }
}
