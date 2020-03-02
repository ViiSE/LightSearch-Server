package lightsearch.server.properties;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.producer.entity.PropertyProducer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("springDatasourcePropertyCreator")
public class SpringDatasourcePropertyCreatorImpl implements PropertyCreator<String, AdminCommand>  {

    private final PropertyProducer propProducer;

    public SpringDatasourcePropertyCreatorImpl(PropertyProducer propProducer) {
        this.propProducer = propProducer;
    }

    @Override
    public Property<String> create(AdminCommand inputData) {
        AdminCommandDatabaseImpl admCmd = (AdminCommandDatabaseImpl) inputData;
        Property<String> propIp = propProducer.getIpPropertyInstance(admCmd.ip());
        Property<String> propPort = propProducer.getPortPropertyInstance(admCmd.port());
        Property<String> propDbName = propProducer.getDatabaseNamePropertyInstance(admCmd.dbName());
        Property<String> propDSUrl = propProducer.getSpringDatasourceURLFirebirdWindowsPropertyInstance(
                new HashMap<>() {{
                    put(propIp.name(), propIp);
                    put(propPort.name(), propPort);
                    put(propDbName.name(), propDbName);
                }});
        Property<String> propDbUsername = propProducer.getSpringDatasourceUsernamePropertyInstance(admCmd.dbUsername());
        Property<String> propDbPass = propProducer.getSpringDatasourcePasswordPropertyInstance(admCmd.password());

        return propProducer.getSpringDatasourcePropertyInstance(
                new HashMap<>() {{
                    put(propDSUrl.name(), propDSUrl);
                    put(propDbUsername.name(), propDbUsername);
                    put(propDbPass.name(), propDbPass);}});
    }
}
