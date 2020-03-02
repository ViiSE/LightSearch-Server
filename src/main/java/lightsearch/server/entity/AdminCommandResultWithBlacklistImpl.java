package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminCommandResultWithBlacklist")
@Scope("prototype")
public class AdminCommandResultWithBlacklistImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final List<String> blacklist;

    public AdminCommandResultWithBlacklistImpl(AdminCommandResult admCmdRes, List<String> blacklist) {
        this.admCmdRes = admCmdRes;
        this.blacklist = blacklist;
    }

    @Override
    public Object formForSend() {
        AdminCommandResultDTO admCmdResDTO = (AdminCommandResultDTO) admCmdRes.formForSend();
        admCmdResDTO.setBlacklist(blacklist);

        return admCmdResDTO;
    }
}
