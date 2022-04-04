package za.co.standardbank.assessment1.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.standardbank.assessment1.adapter.Mappers;
import za.co.standardbank.assessment1.domain.constant.RetryableAction;
import za.co.standardbank.assessment1.domain.constant.UserStatus;
import za.co.standardbank.assessment1.domain.model.RegisterUserRequest;
import za.co.standardbank.assessment1.integration.UserServiceProxy;
import za.co.standardbank.assessment1.method.SaveRequestForRetry;
import za.co.standardbank.assessment1.persistence.entities.User;
import za.co.standardbank.assessment1.persistence.repositories.UserRepository;
import za.co.standardbank.assessment1.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final UserServiceProxy proxy;

    final UserRepository repository;

    final SaveRequestForRetry saveRequestForRetry;

    @Override
    public Long registerUser(final RegisterUserRequest request) {
        // TODO: Maybe validate if user with email address or mobile number already exists

        final User user = Mappers.USER.modelToEntity(request);
        user.setStatus(UserStatus.NEW);
        repository.save(user);

        try {
            proxy.registerUser(user);

            user.setStatus(UserStatus.ACTIVE);
            repository.save(user);

            return user.getId();
        } catch (Exception exception) {
            // TODO: enhance it to catch specific exceptions, since some exception might be valid and we don't want to retry those
            saveRequestForRetry.execute(RetryableAction.REGISTER_USER, user.getId());

            // TODO: Throw Proper exception here with message informing the user that the request will be reprocessed later
            throw new RuntimeException("Failed to submit user, process will be retried later", exception);
        }
    }

}
