package lightsearch.server.properties;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandClientTimeoutImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.producer.entity.PropertyProducer;
import org.springframework.stereotype.Component;

@Component("lightSearchClientTimeoutPropertyCreator")
public class LightSearchClientTimeoutPropertyCreatorImpl implements PropertyCreator<String, AdminCommand>  {

    private final PropertyProducer propProducer;

    public LightSearchClientTimeoutPropertyCreatorImpl(PropertyProducer propProducer) {
        this.propProducer = propProducer;
    }

    @Override
    public Property<String> create(AdminCommand inputData) {
        AdminCommandClientTimeoutImpl admCmd = (AdminCommandClientTimeoutImpl) inputData;
        return propProducer.getClientTimeoutPropertyInstance(admCmd.timeout());
    }
}
