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
    public Property<String> getDbTypePropertyInstance(String value) {
        return new DbTypePropertyImpl(value);
    }

    @Override
    public Property<String> getDbAdditionalPropertyInstance(String value) {
        return new DbAdditionalPropertyImpl(value);
    }

    @Override
    public Property<String> getDatabaseNamePropertyInstance(String value) {
        return new DatabaseNamePropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourceURLPropertyInstance(
            Map<String, Property<String>> properties) {
        return new SpringDatasourceURLPropertyImpl(properties);
    }

    @Override
    public Property<Boolean> getPoolAutoCommitPropertyInstance(boolean value) {
        return new PoolAutoCommitPropertyImpl(value);
    }

    @Override
    public Property<Long> getPoolConnectionTimeoutPropertyInstance(long value) {
        return new PoolConnectionTimeoutPropertyImpl(value);
    }

    @Override
    public Property<Long> getPoolIdleTimeoutPropertyInstance(long value) {
        return new PoolIdleTimeoutPropertyImpl(value);
    }

    @Override
    public Property<Long> getPoolMaxLifeTimePropertyInstance(long value) {
        return new PoolMaxLifeTimePropertyImpl(value);
    }

    @Override
    public Property<Long> getMaximumPoolSizePropertyInstance(long value) {
        return new MaximumPoolSizePropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourceURLH2PropertyInstance() {
        return new SpringDatasourceURLH2PropertyImpl();
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
    public Property<String> getScriptEncodingPropertyInstance(String value) {
        return new ScriptEncodingPropertyImpl(value);
    }

    @Override
    public Property<String> getDriverClassNamePropertyInstance(String value) {
        return new DriverClassNamePropertyImpl(value);
    }

    @Override
    public Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<?>> props) {
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
    public Property<Integer> getClientTimeoutPropertyValueInstance(int value) {
        return new ClientTimeoutPropertyValueImpl(value);
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
