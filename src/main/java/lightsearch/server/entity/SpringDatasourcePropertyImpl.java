package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("springDatasourceProperty")
@Scope("prototype")
public class SpringDatasourcePropertyImpl implements Property<String> {

    private final String name;
    private final Map<String, Property<?>> properties;

    public SpringDatasourcePropertyImpl(Map<String, Property<?>> properties) {
        this.name = DatasourceConstants.URL;
        this.properties = properties;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        String dsUrl = String.valueOf(properties.get(DatasourceConstants.URL).as());
        String dsUsername = String.valueOf(properties.get(DatasourceConstants.USERNAME).as());
        String dsPass = String.valueOf(properties.get(DatasourceConstants.PASSWORD).as());
        String autoCommit = properties.get(DatasourceConstants.POOL_AUTO_COMMIT).name() + "=" + Boolean.parseBoolean(String.valueOf(properties.get(DatasourceConstants.POOL_AUTO_COMMIT).as()));
        String connTout = properties.get(DatasourceConstants.POOL_CONNECTION_TIMEOUT).name() + "=" + Long.parseLong(String.valueOf(properties.get(DatasourceConstants.POOL_CONNECTION_TIMEOUT).as()));
        String idleTout = properties.get(DatasourceConstants.POOL_IDLE_TIMEOUT).name() + "=" + Long.parseLong(String.valueOf(properties.get(DatasourceConstants.POOL_IDLE_TIMEOUT).as()));
        String maxLifeTime = properties.get(DatasourceConstants.POOL_MAX_LIFE_TIME).name() + "=" + Long.parseLong(String.valueOf(properties.get(DatasourceConstants.POOL_MAX_LIFE_TIME).as()));
        String maxPoolSize = properties.get(DatasourceConstants.MAXIMUM_POOL_SIZE).name() + "=" + Long.parseLong(String.valueOf(properties.get(DatasourceConstants.MAXIMUM_POOL_SIZE).as()));
        String scriptEncoding = properties.get(DatasourceConstants.SCRIPT_ENCODING).name() + "=" + properties.get(DatasourceConstants.SCRIPT_ENCODING).as();
        String driverClassName = properties.get(DatasourceConstants.DRIVER_CLASS_NAME).name() + "=" + properties.get(DatasourceConstants.DRIVER_CLASS_NAME).as();

        return String.format(
                "%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                dsUrl,
                dsUsername,
                dsPass,
                autoCommit,
                connTout,
                idleTout,
                maxLifeTime,
                maxPoolSize,
                scriptEncoding,
                driverClassName);
    }
}
