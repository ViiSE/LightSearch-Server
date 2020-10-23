package lightsearch.server.producer.entity;

import lightsearch.server.entity.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("propertyProducer")
public class PropertyProducerImpl implements PropertyProducer {

    private final ApplicationContext ctx;

    public PropertyProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Property<String> getAdminPasswordPropertyInstance(String password) {
        return (AdminPasswordPropertyImpl) ctx.getBean("adminPasswordProperty", password);
    }

    @Override
    public Property<String> getIpPropertyInstance(String value) {
        return (IpPropertyImpl) ctx.getBean("ipProperty", value);
    }

    @Override
    public Property<String> getPortPropertyInstance(int value) {
        return (PortPropertyImpl) ctx.getBean("portProperty", value);
    }

    @Override
    public Property<String> getDbTypePropertyInstance(String value) {
        return (DbTypePropertyImpl) ctx.getBean("dbTypeProperty", value);
    }

    @Override
    public Property<String> getDbAdditionalPropertyInstance(String value) {
        return (DbAdditionalPropertyImpl) ctx.getBean("dbAdditionalProperty", value);
    }

    @Override
    public Property<String> getDatabaseNamePropertyInstance(String value) {
        return (DatabaseNamePropertyImpl) ctx.getBean("databaseNameProperty", value);
    }

    @Override
    public Property<String> getSpringDatasourceURLPropertyInstance(
            Map<String, Property<String>> properties) {
        return (SpringDatasourceURLPropertyImpl) ctx.getBean(
                "springDatasourceURLProperty",
                properties);
    }

    @Override
    public Property<Boolean> getPoolAutoCommitPropertyInstance(boolean value) {
        return (PoolAutoCommitPropertyImpl) ctx.getBean(
                "poolAutoCommitProperty",
                value);
    }

    @Override
    public Property<Long> getPoolConnectionTimeoutPropertyInstance(long value) {
        return (PoolConnectionTimeoutPropertyImpl) ctx.getBean(
                "poolConnectionTimeoutProperty",
                value);
    }

    @Override
    public Property<Long> getPoolIdleTimeoutPropertyInstance(long value) {
        return (PoolIdleTimeoutPropertyImpl) ctx.getBean(
                "poolIdleTimeoutProperty",
                value);
    }

    @Override
    public Property<Long> getPoolMaxLifeTimePropertyInstance(long value) {
        return (PoolMaxLifeTimePropertyImpl) ctx.getBean(
                "poolMaxLifeTimeProperty",
                value);
    }

    @Override
    public Property<Long> getMaximumPoolSizePropertyInstance(long value) {
        return (MaximumPoolSizePropertyImpl) ctx.getBean(
                "maximumPoolSizeProperty",
                value);
    }

    @Override
    public Property<String> getSpringDatasourceURLH2PropertyInstance() {
        return ctx.getBean(SpringDatasourceURLH2PropertyImpl.class);
    }

    @Override
    public Property<String> getSpringDatasourceUsernamePropertyInstance(String value) {
        return (SpringDatasourceUsernamePropertyImpl) ctx.getBean("springDatasourceUsernameProperty", value);
    }

    @Override
    public Property<String> getSpringDatasourcePasswordPropertyInstance(String value) {
        return (SpringDatasourcePasswordPropertyImpl) ctx.getBean("springDatasourcePasswordProperty", value);
    }

    @Override
    public Property<String> getScriptEncodingPropertyInstance(String value) {
        return (ScriptEncodingPropertyImpl) ctx.getBean("scriptEncodingProperty", value);
    }

    @Override
    public Property<String> getDriverClassNamePropertyInstance(String value) {
        return (DriverClassNamePropertyImpl) ctx.getBean("driverClassNameProperty", value);
    }

    @Override
    public Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<?>> props) {
        return (SpringDatasourcePropertyImpl) ctx.getBean("springDatasourceProperty", props);
    }

    @Override
    public Property<String> getSimplePropertyInstance(String name, String value) {
        return (SimplePropertyImpl) ctx.getBean("simpleProperty", name, value);
    }

    @Override
    public Property<String> getClientTimeoutPropertyInstance(int value) {
        return (ClientTimeoutPropertyImpl) ctx.getBean("clientTimeoutProperty", value);
    }

    @Override
    public Property<Integer> getClientTimeoutPropertyValueInstance(int value) {
        return (ClientTimeoutPropertyValueImpl) ctx.getBean("clientTimeoutPropertyValue", value);
    }

    @Override
    public Property<String> getDatasourceDriverClassNamePropertyInstance(String value) {
        return (DatasourceDriverClassNamePropertyImpl) ctx.getBean("datasourceDriverClassNameProperty", value);
    }

    @Override
    public Property<String> getDatasourceUsernamePropertyInstance(String value) {
        return (DatasourceUsernamePropertyImpl) ctx.getBean("datasourceUsernameProperty", value);
    }

    @Override
    public Property<String> getDatasourcePasswordPropertyInstance(String value) {
        return (DatasourcePasswordPropertyImpl) ctx.getBean("datasourcePasswordProperty", value);
    }

    @Override
    public Property<String> getDatasourceURLPropertyInstance(String value) {
        return (DatasourceURLPropertyImpl) ctx.getBean("datasourceURLProperty", value);
    }
}
