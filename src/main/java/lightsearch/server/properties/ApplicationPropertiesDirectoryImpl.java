package lightsearch.server.properties;

import lightsearch.server.initialization.CurrentServerDirectory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("applicationPropertiesDirectory")
public class ApplicationPropertiesDirectoryImpl implements PropertiesDirectory {

    private final CurrentServerDirectory serverDirectory;

    public ApplicationPropertiesDirectoryImpl(CurrentServerDirectory serverDirectory) {
        this.serverDirectory = serverDirectory;
    }

    @Override
    public String name() {
        return serverDirectory.name() + "config" + File.separator + "application.properties";
    }
}
