package lightsearch.server.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("springDatasourceProperty")
@Scope("prototype")
public class SpringDatasourcePropertyImpl implements Property<String> {

    private final String name;
    private final Map<String, Property<String>> properties;

    public SpringDatasourcePropertyImpl(Map<String, Property<String>> properties) {
        this.name = "spring.datasource";
        this.properties = properties;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        String dsUrl = properties.get("spring.datasource.url").as();
        String dsUsername = properties.get("spring.datasource.username").as();
        String dsPass = properties.get("spring.datasource.password").as();
        return String.format(
                "%s\n%s\n%s\n",
                dsUrl,
                dsUsername,
                dsPass);
    }
}
