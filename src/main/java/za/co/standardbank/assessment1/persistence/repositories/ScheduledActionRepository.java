package za.co.standardbank.assessment1.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.standardbank.assessment1.persistence.entities.ScheduledAction;

import java.util.Collection;

@Repository
public interface ScheduledActionRepository extends JpaRepository<ScheduledAction, za.co.standardbank.assessment1.domain.constant.ScheduledAction> {
    Collection<ScheduledAction> findByActiveIsTrue();
}
