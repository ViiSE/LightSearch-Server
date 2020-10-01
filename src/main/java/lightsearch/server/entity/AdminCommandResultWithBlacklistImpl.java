package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultWithBlacklistDTO;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
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
        AdminCommandSimpleResultDTO admCmdResDTO = (AdminCommandSimpleResultDTO) admCmdRes.formForSend();

        AdminCommandResultWithBlacklistDTO resDTO = new AdminCommandResultWithBlacklistDTO(
                admCmdResDTO.getIsDone(),
                admCmdResDTO.getMessage());
        resDTO.setBlacklist(blacklist);

        return resDTO;
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
