package za.co.standardbank.assessment1.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import za.co.standardbank.assessment1.integration.model.RegisterUserRequest;

@Component
@FeignClient(name = "user-service", path = "/api", url = "http://localhost:8080")
public interface UserServiceClient {

//    @PostMapping("/user")
//    void registerUser(RegisterUserRequest request);

    // TODO: The above commented out code should be reverted once the service is ready to be consumed
    // TODO: the below code is added for testing purposes
    default void registerUser(RegisterUserRequest request) {
        throw new RuntimeException("Service not reachable");
    }
}
