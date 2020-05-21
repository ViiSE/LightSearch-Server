package lightsearch.server.initialization;

import lightsearch.server.entity.Client;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("serverInitializerTest")
public class ServerInitializerTestImpl implements ServerInitializer {

    private final ClientsService<String, Client, List<Client>> clientsService;
    private final BlacklistCreator blacklistCreator;

    public ServerInitializerTestImpl(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client, List<Client>> clientsService,
            BlacklistCreator blacklistCreator) {
        this.clientsService = clientsService;
        this.blacklistCreator = blacklistCreator;
    }

    @Override
    public void initialize() {
        clientsService.addClient("111111111111111", "client 1");
        clientsService.addClient("222222222222222", "client 2");
        clientsService.addClient("333333333333333",  "client 3");
        clientsService.addClient("444444444444444",  "client 4");
        clientsService.addClient("555555555555555",  "client 5");
        clientsService.addClient("666666666666666",  "client 6");
        clientsService.addClient("777777777777777",  "client 7");
        clientsService.addClient("888888888888888",  "client 8");
        clientsService.addClient("999999999999999",  "client 9");
        clientsService.addClient("111",  "client 10");
        clientsService.addClient("222", "client 11");
        clientsService.addClient("333",  "client 12");
        clientsService.addClient("444",  "client 13");
        clientsService.addClient("555",  "client 14");
        clientsService.addClient("666",  "client 15");
        clientsService.addClient("777",  "client 16");
        clientsService.addClient("888",  "client 17");
        clientsService.addClient("999",  "client 18");
        clientsService.addClient("11",  "client 19");
        clientsService.addClient("22",  "client 20");
        clientsService.addClient("33",  "client 21");
        clientsService.addClient("44",  "client 22");
        clientsService.addClient("55",  "client 23");
        clientsService.addClient("66",  "client 24");
        clientsService.addClient("77",  "client 25");
        clientsService.addClient("88",  "client 26");
        clientsService.addClient("99",  "client 27");

        blacklistCreator.create();
    }
}
