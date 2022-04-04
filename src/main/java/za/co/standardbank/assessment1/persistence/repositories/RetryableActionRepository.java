package za.co.standardbank.assessment1.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.standardbank.assessment1.persistence.entities.RetryableAction;

import java.util.Collection;

@Repository
public interface RetryableActionRepository extends JpaRepository<RetryableAction, za.co.standardbank.assessment1.domain.constant.RetryableAction> {
    Collection<RetryableAction> findByActiveIsTrue();
}
