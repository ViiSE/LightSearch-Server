package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandSimpleResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandResultSimple")
@Scope("prototype")
public class AdminCommandResultSimpleImpl implements AdminCommandResult {

    private final boolean isDone;
    private final String message;

    public AdminCommandResultSimpleImpl(boolean isDone, String message) {
        this.isDone = isDone;
        this.message = message;
    }

    @Override
    public Object formForSend() {
        return new AdminCommandSimpleResultDTO(isDone, message);
    }
}
