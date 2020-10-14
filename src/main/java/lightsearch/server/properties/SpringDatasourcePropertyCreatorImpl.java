package lightsearch.server.properties;

import lightsearch.server.data.DatasourcePoolDTO;
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
        Property<String> propIp = propProducer.getIpPropertyInstance(admCmd.host());
        Property<String> propPort = propProducer.getPortPropertyInstance(admCmd.port());
        Property<String> propDbName = propProducer.getDatabaseNamePropertyInstance(admCmd.dbName());
        Property<String> propDbType = propProducer.getDbTypePropertyInstance(admCmd.dbType());
        Property<String> propAdditional = propProducer.getDbAdditionalPropertyInstance(admCmd.additional());
        Property<String> propDSUrl = propProducer.getSpringDatasourceURLPropertyInstance(
                new HashMap<>() {{
                    put(propIp.name(), propIp);
                    put(propPort.name(), propPort);
                    put(propDbName.name(), propDbName);
                    put(propDbType.name(), propDbType);
                    put(propAdditional.name(), propAdditional);
                }});
        Property<String> propDbUsername = propProducer.getSpringDatasourceUsernamePropertyInstance(admCmd.dbUsername());
        Property<String> propDbPass = propProducer.getSpringDatasourcePasswordPropertyInstance(admCmd.password());
        Property<String> propScriptEncoding = propProducer.getScriptEncodingPropertyInstance(admCmd.scriptEncoding());
        Property<String> driverClassName = propProducer.getDriverClassNamePropertyInstance(admCmd.driverClassName());

        DatasourcePoolDTO poolDTO = admCmd.poolDTO();
        Property<Boolean> propAutoCommit = propProducer.getPoolAutoCommitPropertyInstance(poolDTO.isAutoCommit());
        Property<Long> propConnectionTimeout = propProducer.getPoolConnectionTimeoutPropertyInstance(poolDTO.getConnectionTimeout());
        Property<Long> propIdleTimeout = propProducer.getPoolIdleTimeoutPropertyInstance(poolDTO.getIdleTimeout());
        Property<Long> propMaxLifeTime = propProducer.getPoolMaxLifeTimePropertyInstance(poolDTO.getMaxLifeTime());
        Property<Long> propMaximumPoolSize = propProducer.getMaximumPoolSizePropertyInstance(poolDTO.getMaximumPoolSize());

        return propProducer.getSpringDatasourcePropertyInstance(
                new HashMap<>() {{
                    put(propDSUrl.name(), propDSUrl);
                    put(propDbUsername.name(), propDbUsername);
                    put(propDbPass.name(), propDbPass);
                    put(propAutoCommit.name(), propAutoCommit);
                    put(propConnectionTimeout.name(), propConnectionTimeout);
                    put(propIdleTimeout.name(), propIdleTimeout);
                    put(propMaxLifeTime.name(), propMaxLifeTime);
                    put(propMaximumPoolSize.name(), propMaximumPoolSize);
                    put(propScriptEncoding.name(), propScriptEncoding);
                    put(driverClassName.name(), driverClassName);
                }});
    }
}
