package lightsearch.server.producer.entity;

import lightsearch.server.entity.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("propertyProducerDefault")
public class PropertyProducerDefaultImpl implements PropertyProducer {

    private final ApplicationContext ctx;

    public PropertyProducerDefaultImpl(ApplicationContext ctx) {
        this.ctx = ctx;
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
    public Property<String> getDatabaseNamePropertyInstance(String value) {
        return (DatabaseNamePropertyImpl) ctx.getBean("databaseNameProperty", value);
    }

    @Override
    public Property<String> getSpringDatasourceURLFirebirdWindowsPropertyInstance(
            Map<String, Property<String>> properties) {
        return (SpringDatasourceURLFirebirdWindowsPropertyImpl) ctx.getBean(
                "springDatasourceURLFirebirdWindowsProperty",
                properties);
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
    public Property<String> getSpringDatasourcePropertyInstance(Map<String, Property<String>> props) {
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
