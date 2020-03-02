package lightsearch.server.entity;

import lightsearch.server.constants.AdminCommands;
import lightsearch.server.data.AdminCommandDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandDatabase")
@Scope("prototype")
public class AdminCommandDatabaseImpl implements AdminCommand {

    private final String ip;
    private final int port;
    private final String dbName;
    private final String dbUsername;
    private final String password;

    public AdminCommandDatabaseImpl(AdminCommandDTO adminCommandDTO) {
        this.ip = adminCommandDTO.getIp();
        this.port = adminCommandDTO.getPort();
        this.dbName = adminCommandDTO.getDbName();
        this.dbUsername = adminCommandDTO.getUsername();
        this.password = adminCommandDTO.getPassword();
    }

    @Override
    public String name() {
        return AdminCommands.CHANGE_DATABASE;
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }

    public String dbName() {
        return dbName;
    }

    public String dbUsername() {
        return dbUsername;
    }

    public String password() {
        return password;
    }
}
