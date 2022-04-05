package za.co.standardbank.assessment1.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;
import za.co.standardbank.assessment1.persistence.entities.RetryAction;

import java.util.Collection;

@Repository
public interface RetryActionRepository extends JpaRepository<RetryAction, Long> {
    Collection<RetryAction> findByStatusAndNumberOfAttemptsLessThanEqual(RequestStatus status, int numberOfAttempts);
}
