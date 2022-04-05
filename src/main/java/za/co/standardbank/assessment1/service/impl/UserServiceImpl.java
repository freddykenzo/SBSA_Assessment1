package za.co.standardbank.assessment1.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.standardbank.assessment1.adapter.Mappers;
import za.co.standardbank.assessment1.domain.constant.ScheduledAction;
import za.co.standardbank.assessment1.domain.constant.UserStatus;
import za.co.standardbank.assessment1.domain.model.RegisterUserRequest;
import za.co.standardbank.assessment1.integration.UserServiceProxy;
import za.co.standardbank.assessment1.method.SaveRequestForRetry;
import za.co.standardbank.assessment1.persistence.entities.User;
import za.co.standardbank.assessment1.persistence.repositories.UserRepository;
import za.co.standardbank.assessment1.service.UserService;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final UserServiceProxy proxy;

    final UserRepository repository;

    final SaveRequestForRetry saveRequestForRetry;

    @Override
    public Long registerUser(final RegisterUserRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    log.warn("Existing user with Provided email {}", request.getEmail());
                    throw new RuntimeException("Existing user with Provided email");
                });

        final User user = Mappers.USER.modelToEntity(request);
        user.setStatus(UserStatus.NEW);
        repository.save(user);

        try {
            proxy.registerUser(user);

            user.setStatus(UserStatus.ACTIVE);
            repository.save(user);

            return user.getId();
        } catch (final Exception exception) {
            // TODO: enhance it to catch specific exceptions, since some exception might be valid and we don't want to retry those
            saveRequestForRetry.execute(ScheduledAction.REGISTER_USER, user.getId());

            // TODO: Throw Proper exception here with message informing the user that the request will be reprocessed later
            throw new RuntimeException("Failed to submit user, process will be retried later", exception);
        }
    }

}
