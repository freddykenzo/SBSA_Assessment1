package za.co.standardbank.assessment1.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "retryable_action")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetryableAction {
    @Id
    @Column
    @Enumerated(EnumType.STRING)
    private za.co.standardbank.assessment1.domain.constant.RetryableAction id;

    @Column(name = "max_attempt")
    private Integer maxAttempt;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;
}
