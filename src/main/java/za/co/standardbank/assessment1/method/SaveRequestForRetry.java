package za.co.standardbank.assessment1.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;
import za.co.standardbank.assessment1.persistence.entities.ScheduledAction;
import za.co.standardbank.assessment1.persistence.entities.RetryAction;
import za.co.standardbank.assessment1.persistence.repositories.RetryActionRepository;
import za.co.standardbank.assessment1.persistence.repositories.ScheduledActionRepository;

@Component
@AllArgsConstructor
public class SaveRequestForRetry {

    private final ObjectMapper mapper;

    private final ScheduledActionRepository scheduledActionRepository;

    private final RetryActionRepository retryActionRepository;

    public void execute(final za.co.standardbank.assessment1.domain.constant.ScheduledAction action, final Long entityId) {
        saveRequest(action, null, entityId);
    }

    public void execute(final za.co.standardbank.assessment1.domain.constant.ScheduledAction action, final Object object) {
        saveRequest(action, object, null);
    }

    private void saveRequest(final za.co.standardbank.assessment1.domain.constant.ScheduledAction actionId, final Object request, final Long entityId) {
        try {
            // TODO: throw proper exception, missing required entity
            final ScheduledAction action = scheduledActionRepository.findById(actionId)
                    .orElseThrow();

            final RetryAction retryAction = RetryAction.builder()
                    .action(action)
                    .request(request != null ? mapper.writeValueAsString(request) : null)
                    .entityId(entityId)
                    .numberOfAttempts(1)
                    .status(RequestStatus.FAILED)
                    .build();

            retryActionRepository.save(retryAction);
        } catch (final JsonProcessingException e) {
            // TODO: Throw Proper exception here
            throw new RuntimeException("Failed to create Retryable action for User Registration", e);
        }
    }
}
