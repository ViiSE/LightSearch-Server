package lightsearch.server.entity;

public interface Property<T> {
    String name();
    T as();
}
