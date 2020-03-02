package lightsearch.server.producer.entity;

import lightsearch.server.entity.*;

import java.util.Map;

public class PropertyProducerTestImpl implements PropertyProducer {

    @Override
    public Property<String> getIpPropertyInstance(String value) {
        return new IpPropertyImpl(value);
    }

    @Override
    public Property<String> getPortPropertyInstance(int value) {
        return new PortPropertyImpl(value);
    }

    @Override
    public Property<String> getDatabaseNamePropertyInstance(String value) {
        return new DatabaseNamePropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourceURLFirebirdWindowsPropertyInstance(
            Map<String, Property<String>> properties) {
        return new SpringDatasourceURLFirebirdWindowsPropertyImpl(properties);
    }

    @Override
    public Property<String> getSpringDatasourceUsernamePropertyInstance(String value) {
        return new SpringDatasourceUsernamePropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourcePasswordPropertyInstance(String value) {
        return new SpringDatasourcePasswordPropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<String>> props) {
        return new SpringDatasourcePropertyImpl(props);
    }

    @Override
    public Property<String> getSimplePropertyInstance(String name, String value) {
        return new SimplePropertyImpl(name, value);
    }

    @Override
    public Property<String> getClientTimeoutPropertyInstance(int value) {
        return new ClientTimeoutPropertyImpl(value);
    }

    @Override
    public Property<String> getDatasourceDriverClassNamePropertyInstance(String value) {
        return new DatasourceDriverClassNamePropertyImpl(value);
    }

    @Override
    public Property<String> getDatasourceUsernamePropertyInstance(String value) {
        return new DatasourceUsernamePropertyImpl(value);
    }

    @Override
    public Property<String> getDatasourcePasswordPropertyInstance(String value) {
        return new DatasourcePasswordPropertyImpl(value);
    }

    @Override
    public Property<String> getDatasourceURLPropertyInstance(String value) {
        return new DatasourceURLPropertyImpl(value);
    }
}
