package lightsearch.server.producer.entity;

import lightsearch.server.entity.Property;

import java.util.Map;

public interface PropertyProducer {
    Property<String> getIpPropertyInstance(String value);
    Property<String> getPortPropertyInstance(int value);
    Property<String> getDatabaseNamePropertyInstance(String value);
    Property<String> getSpringDatasourceURLFirebirdWindowsPropertyInstance(Map<String, Property<String>> properties);
    Property<String> getSpringDatasourceUsernamePropertyInstance(String value);
    Property<String> getSpringDatasourcePasswordPropertyInstance(String value);
    Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<String>> props);
    Property<String> getSimplePropertyInstance(String name, String value);
    Property<String> getClientTimeoutPropertyInstance(int value);
    Property<String> getDatasourceDriverClassNamePropertyInstance(String value);
    Property<String> getDatasourceUsernamePropertyInstance(String value);
    Property<String> getDatasourcePasswordPropertyInstance(String value);
    Property<String> getDatasourceURLPropertyInstance(String value);
}
