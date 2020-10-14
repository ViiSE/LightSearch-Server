package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultWithClientTimeoutDTO;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandResultWithClientTimeout")
@Scope("prototype")
public class AdminCommandResultWithClientTimeoutImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final long clientTimeout;

    public AdminCommandResultWithClientTimeoutImpl(AdminCommandResult admCmdRes, long clientTimeout) {
        this.admCmdRes = admCmdRes;
        this.clientTimeout = clientTimeout;
    }

    @Override
    public Object formForSend() {
        AdminCommandSimpleResultDTO admCmdResDTO = (AdminCommandSimpleResultDTO) admCmdRes.formForSend();

        AdminCommandResultWithClientTimeoutDTO resDTO = new AdminCommandResultWithClientTimeoutDTO(
                admCmdResDTO.getIsDone(),
                admCmdResDTO.getMessage());
        resDTO.setClientTimeout(clientTimeout);

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
