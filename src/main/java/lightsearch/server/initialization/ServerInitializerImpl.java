package lightsearch.server.initialization;

import org.springframework.stereotype.Service;

@Service("serverInitializer")
public class ServerInitializerImpl implements ServerInitializer {

    private final BlacklistCreator blacklistCreator;

    public ServerInitializerImpl(BlacklistCreator blacklistCreator) {
        this.blacklistCreator = blacklistCreator;
    }

    @Override
    public void initialize() {
        blacklistCreator.create();
    }
}
