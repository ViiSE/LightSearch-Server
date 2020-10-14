package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("springDatasourceURLProperty")
@Scope("prototype")
public class SpringDatasourceURLPropertyImpl implements Property<String> {

    private final String name;
    private final Map<String, Property<String>> properties;

    public SpringDatasourceURLPropertyImpl(Map<String, Property<String>> properties) {
        this.name = "spring.datasource.url";
        this.properties = properties;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        String ip = properties.get("ip").as();
        String port = properties.get("port").as();
        String dbName = properties.get("dbName").as();
        String dbType = properties.get("dbType").as();
        String additional = properties.get("additional").as();
        return String.format(
                "%s=%s:%s://%s:%s%s?%s",
                name,
                "jdbc",
                dbType,
                ip,
                port,
                dbName,
                additional);
    }
}
