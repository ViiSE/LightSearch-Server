package lightsearch.server.initialization;

import org.springframework.stereotype.Service;

@Service("serverInitializerDefault")
public class ServerInitializerDefaultImpl implements ServerInitializer {

    private final BlacklistCreator blacklistCreator;

    public ServerInitializerDefaultImpl(BlacklistCreator blacklistCreator) {
        this.blacklistCreator = blacklistCreator;
    }

    @Override
    public void initialize() {
        blacklistCreator.create();
    }
}
