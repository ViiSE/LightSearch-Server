package lightsearch.server.producer.entity;

import lightsearch.server.entity.Property;

import java.util.Map;

public interface PropertyProducer {
    Property<String> getIpPropertyInstance(String value);
    Property<String> getPortPropertyInstance(int value);
    Property<String> getDbTypePropertyInstance(String value);
    Property<String> getDbAdditionalPropertyInstance(String value);
    Property<String> getDatabaseNamePropertyInstance(String value);
    Property<String> getSpringDatasourceURLPropertyInstance(Map<String, Property<String>> properties);
    Property<Boolean> getPoolAutoCommitPropertyInstance(boolean value);
    Property<Long> getPoolConnectionTimeoutPropertyInstance(long value);
    Property<Long> getPoolIdleTimeoutPropertyInstance(long value);
    Property<Long> getPoolMaxLifeTimePropertyInstance(long value);
    Property<Long> getMaximumPoolSizePropertyInstance(long value);
    Property<String> getSpringDatasourceURLH2PropertyInstance();
    Property<String> getSpringDatasourceUsernamePropertyInstance(String value);
    Property<String> getSpringDatasourcePasswordPropertyInstance(String value);
    Property<String> getScriptEncodingPropertyInstance(String value);
    Property<String> getDriverClassNamePropertyInstance(String value);
    Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<?>> props);
    Property<String> getSimplePropertyInstance(String name, String value);
    Property<String> getClientTimeoutPropertyInstance(int value);
    Property<Integer> getClientTimeoutPropertyValueInstance(int value);
    Property<String> getDatasourceDriverClassNamePropertyInstance(String value);
    Property<String> getDatasourceUsernamePropertyInstance(String value);
    Property<String> getDatasourcePasswordPropertyInstance(String value);
    Property<String> getDatasourceURLPropertyInstance(String value);
}
