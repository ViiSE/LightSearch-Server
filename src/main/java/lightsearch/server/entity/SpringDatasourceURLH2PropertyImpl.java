package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("springDatasourceURLH2Property")
@Scope("prototype")
public class SpringDatasourceURLH2PropertyImpl implements Property<String> {

    private final String name;

    public SpringDatasourceURLH2PropertyImpl() {
        this.name = "spring.datasource.url";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        return String.format(
                "%s=%s",
                name,
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    }
}
