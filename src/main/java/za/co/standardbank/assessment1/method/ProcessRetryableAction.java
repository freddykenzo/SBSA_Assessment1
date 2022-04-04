package za.co.standardbank.assessment1.method;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;
import za.co.standardbank.assessment1.domain.constant.UserStatus;
import za.co.standardbank.assessment1.integration.UserServiceProxy;
import za.co.standardbank.assessment1.persistence.entities.RetryableAction;
import za.co.standardbank.assessment1.persistence.entities.User;
import za.co.standardbank.assessment1.persistence.repositories.ScheduledActionRepository;
import za.co.standardbank.assessment1.persistence.repositories.UserRepository;
import za.co.standardbank.assessment1.service.UserService;

@Slf4j
@Component
@AllArgsConstructor
public class ProcessRetryableAction {

    private final ScheduledActionRepository scheduledActionRepository;

    private final UserServiceProxy userServiceProxy;

    private final UserRepository userRepository;

    @Transactional
    public void execute(final RetryableAction action) {
        switch (action.getId()) {
            case REGISTER_USER:
                retryRegisterUsers(action);
                break;
            default:
                // TODO: create and throw proper exception
                throw new RuntimeException("Not implemented yet");
        }
    }

    private void retryRegisterUsers(final RetryableAction action) {
        log.debug("Retrying User registrations");
        // TODO: enhance this to process it in batch
        scheduledActionRepository.findByStatusAndNumberOfAttemptsLessThanEqual(RequestStatus.FAILED, action.getMaxAttempt())
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
                    scheduledActionRepository.save(scheduledAction);
                });

        log.debug("Complete User registration retry");
    }
}
