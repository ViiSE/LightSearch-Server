package lightsearch.server.properties;

import lightsearch.server.entity.Property;

public interface PropertyCreator<T, V> {
    Property<T> create(V inputData);
}
