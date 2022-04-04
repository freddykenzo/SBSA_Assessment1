package za.co.standardbank.assessment1.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.standardbank.assessment1.persistence.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
