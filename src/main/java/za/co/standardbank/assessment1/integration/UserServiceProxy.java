package za.co.standardbank.assessment1.integration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import za.co.standardbank.assessment1.adapter.Mappers;
import za.co.standardbank.assessment1.integration.client.UserServiceClient;
import za.co.standardbank.assessment1.persistence.entities.User;

@Component
@AllArgsConstructor
public class UserServiceProxy {

    private final UserServiceClient client;

    public void registerUser(final User request) {
        client.registerUser(Mappers.USER.entityToProxy(request));
    }
}
