package lightsearch.server.entity;

import lightsearch.server.constants.DatasourceConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("scriptEncodingProperty")
@Scope("prototype")
public class ScriptEncodingPropertyImpl implements Property<String> {

    private final String name;
    private final String value;

    public ScriptEncodingPropertyImpl(String value) {
        this.name = DatasourceConstants.SCRIPT_ENCODING;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String as() {
        return value;
    }
}
