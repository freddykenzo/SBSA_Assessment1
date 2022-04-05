package za.co.standardbank.assessment1.method;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.standardbank.assessment1.persistence.entities.ScheduledAction;

@Slf4j
@Component
@AllArgsConstructor
public class ProcessScheduledAction {

    private final RetryRegisterUsers retryRegisterUsers;

    private final ProcessUserCsvFileRead processUserCsvFileRead;

    @Transactional
    public void execute(final ScheduledAction action) {
        switch (action.getId()) {
            case REGISTER_USER:
                retryRegisterUsers.execute(action);
                break;
            case USER_FILE_UPLOAD:
                processUserCsvFileRead.execute();
                break;
            default:
                log.warn("This action is not implemented yet {}", action.getId());
                // TODO: create and throw proper exception
                throw new RuntimeException("Not implemented yet");
        }
    }
}
