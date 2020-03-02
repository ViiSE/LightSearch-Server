package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("springDatasourceURLFirebirdWindowsProperty")
@Scope("prototype")
public class SpringDatasourceURLFirebirdWindowsPropertyImpl implements Property<String> {

    private final String name;
    private final Map<String, Property<String>> properties;

    public SpringDatasourceURLFirebirdWindowsPropertyImpl(Map<String, Property<String>> properties) {
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
        return String.format(
                "%s=%s%s:%s%s%s",
                name,
                "jdbc:firebirdsql://", ip, port, dbName,
                "?encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251");
    }
}
