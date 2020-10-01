package lightsearch.server.entity;

public interface AdminCommandResult {
    Object formForSend();
    boolean isDone();
    String message();
}
