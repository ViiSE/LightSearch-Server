package lightsearch.server.properties;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandChangePassImpl;
import lightsearch.server.entity.AdminCommandClientTimeoutImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.producer.entity.PropertyProducer;
import lightsearch.server.security.HashAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("adminPasswordPropertyCreator")
public class AdminPasswordPropertyCreatorImpl implements PropertyCreator<String, AdminCommand>  {

    private final HashAlgorithm hashAlgorithm;
    private final PropertyProducer propProducer;

    public AdminPasswordPropertyCreatorImpl(
            @Qualifier("hashAlgorithmsBCrypt") HashAlgorithm hashAlgorithm,
            PropertyProducer propProducer) {
        this.hashAlgorithm = hashAlgorithm;
        this.propProducer = propProducer;
    }

    @Override
    public Property<String> create(AdminCommand inputData) {
        AdminCommandChangePassImpl admCmd = (AdminCommandChangePassImpl) inputData;
        String hashPass = "{bcrypt}" + hashAlgorithm.digest(admCmd.password());
        return propProducer.getAdminPasswordPropertyInstance(hashPass);
    }
}
