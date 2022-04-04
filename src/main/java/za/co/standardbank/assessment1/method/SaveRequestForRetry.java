package za.co.standardbank.assessment1.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;
import za.co.standardbank.assessment1.persistence.entities.RetryableAction;
import za.co.standardbank.assessment1.persistence.entities.ScheduledAction;
import za.co.standardbank.assessment1.persistence.repositories.RetryableActionRepository;
import za.co.standardbank.assessment1.persistence.repositories.ScheduledActionRepository;

@Component
@AllArgsConstructor
public class SaveRequestForRetry {

    private final ObjectMapper mapper;

    private final RetryableActionRepository retryableActionRepository;

    private final ScheduledActionRepository scheduledActionRepository;

    public void execute(final za.co.standardbank.assessment1.domain.constant.RetryableAction action, final Long entityId) {
        saveRequest(action, null, entityId);
    }

    public void execute(final za.co.standardbank.assessment1.domain.constant.RetryableAction action, final Object object) {
        saveRequest(action, object, null);
    }

    private void saveRequest(final za.co.standardbank.assessment1.domain.constant.RetryableAction actionId, final Object request, final Long entityId) {
        try {
            // TODO: throw proper exception, missing required entity
            final RetryableAction action = retryableActionRepository.findById(actionId)
                    .orElseThrow();

            final ScheduledAction scheduledAction = ScheduledAction.builder()
                    .action(action)
                    .request(request != null ? mapper.writeValueAsString(request) : null)
                    .entityId(entityId)
                    .numberOfAttempts(1)
                    .status(RequestStatus.FAILED)
                    .build();

            scheduledActionRepository.save(scheduledAction);
        } catch (final JsonProcessingException e) {
            // TODO: Throw Proper exception here
            throw new RuntimeException("Failed to create Retryable action for User Registration", e);
        }
    }
}
