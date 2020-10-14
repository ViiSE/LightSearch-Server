package lightsearch.server.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lightsearch.server.constants.AdminCommands;
import lightsearch.server.data.AdminChangeDatabaseCommandDTO;
import lightsearch.server.data.DatasourcePoolDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandDatabase")
@Scope("prototype")
public class AdminCommandDatabaseImpl implements AdminCommand {

    private final String host;
    private final int port;
    private final String dbName;
    private final String dbUsername;
    private final String password;
    private final String dbType;
    private final String additional;
    private final String scriptEncoding;
    private final String driverClassName;
    private final DatasourcePoolDTO poolDTO;

    public AdminCommandDatabaseImpl(AdminChangeDatabaseCommandDTO adminCommandDTO) {
        this.host = adminCommandDTO.getHost();
        this.port = adminCommandDTO.getPort();
        this.dbName = adminCommandDTO.getDbName();
        this.dbUsername = adminCommandDTO.getUsername();
        this.password = adminCommandDTO.getPassword();
        this.dbType = adminCommandDTO.getDbType();
        this.additional = adminCommandDTO.getAdditional();
        this.scriptEncoding = adminCommandDTO.getScriptEncoding();
        this.driverClassName = adminCommandDTO.getDriverClassName();
        this.poolDTO = adminCommandDTO.getPool();
    }

    @Override
    public String name() {
        return AdminCommands.CHANGE_DATABASE;
    }

    public String host() {
        return host;
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

    public String additional() {
        return additional;
    }

    public String dbType() {
        return dbType;
    }

    public String scriptEncoding() {
        return scriptEncoding;
    }

    public String driverClassName() {
        return driverClassName;
    }

    public DatasourcePoolDTO poolDTO() {
        return poolDTO;
    }
}
