package za.co.standardbank.assessment1.method;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;
import za.co.standardbank.assessment1.domain.constant.UserStatus;
import za.co.standardbank.assessment1.integration.UserServiceProxy;
import za.co.standardbank.assessment1.persistence.entities.ScheduledAction;
import za.co.standardbank.assessment1.persistence.entities.User;
import za.co.standardbank.assessment1.persistence.repositories.RetryActionRepository;
import za.co.standardbank.assessment1.persistence.repositories.UserRepository;

@Component
@AllArgsConstructor
@Slf4j
public class RetryRegisterUsers {

    private final RetryActionRepository retryActionRepository;

    private final UserServiceProxy userServiceProxy;

    private final UserRepository userRepository;

    public void execute(final ScheduledAction action) {
        log.debug("Retrying User registrations");
        // TODO: enhance this to process it in batch
        retryActionRepository.findByStatusAndNumberOfAttemptsLessThanEqual(RequestStatus.FAILED, action.getMaxAttempt())
                .forEach(scheduledAction -> {
                    try {
                        final User user = userRepository.findById(scheduledAction.getEntityId())
                                .orElseThrow(); // TODO: create and throw proper exception

                        userServiceProxy.registerUser(user);

                        user.setStatus(UserStatus.ACTIVE);
                        userRepository.save(user);

                        scheduledAction.setStatus(RequestStatus.SUCCESS);
                    } catch (final Exception exception) { // TODO: enhance it to catch specific exceptions, since some exception might be valid and we don't want to retry those
                        scheduledAction.setNumberOfAttempts(scheduledAction.getNumberOfAttempts() + 1);
                    }
                    retryActionRepository.save(scheduledAction);
                });

        log.debug("Complete User registration retry");
    }
}
