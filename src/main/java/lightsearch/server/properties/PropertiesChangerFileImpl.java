package lightsearch.server.properties;

import lightsearch.server.entity.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.WriterException;
import lightsearch.server.producer.properties.PropertiesWriterProducer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("propertiesChangerFile")
public class PropertiesChangerFileImpl implements PropertiesChanger<Void, Property<String>> {

    private final PropertiesDirectory propsDir;
    private final PropertiesChanger<List<String>, Property<String>> propsChanger;
    private final PropertiesWriterProducer propsWriterProducer;

    public PropertiesChangerFileImpl(
            PropertiesDirectory propsDir,
            PropertiesChanger<List<String>, Property<String>> propsChanger,
            PropertiesWriterProducer propsWriterProducer) {
        this.propsDir = propsDir;
        this.propsChanger = propsChanger;
        this.propsWriterProducer = propsWriterProducer;
    }

    @Override
    public Void change(Property<String> props) throws PropertiesException {
        try {
            List<String> propsCh = propsChanger.change(props);
            propsWriterProducer.getPropertiesFileWriterListOfStringInstance(
                    propsDir.name(),
                    false).write(propsCh);

            return null;
        } catch (WriterException ex) {
            throw new PropertiesException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
