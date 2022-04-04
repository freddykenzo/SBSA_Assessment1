package za.co.standardbank.assessment1.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.standardbank.assessment1.domain.constant.RequestStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scheduled_action")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduledAction {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_attempts")
    private Integer numberOfAttempts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action")
    private RetryableAction action;

    @Column(name = "request")
    private String request;

    @Column(name = "entityId")
    private Long entityId;

    @Column(name = "status")
    private RequestStatus status;
}
