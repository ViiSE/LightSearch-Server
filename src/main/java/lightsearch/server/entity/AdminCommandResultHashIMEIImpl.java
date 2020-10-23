package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultWithHashIMEIDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandResultHashIMEI")
@Scope("prototype")
public class AdminCommandResultHashIMEIImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final String hashIMEI;

    public AdminCommandResultHashIMEIImpl(AdminCommandResult admCmdRes, String hashIMEI) {
        this.admCmdRes = admCmdRes;
        this.hashIMEI = hashIMEI;
    }

    @Override
    public Object formForSend() {
        return new AdminCommandResultWithHashIMEIDTO(admCmdRes.isDone(), admCmdRes.message()) {{
            setHashIMEI(hashIMEI);
        }};
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
