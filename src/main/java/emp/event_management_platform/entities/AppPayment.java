package emp.event_management_platform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull
    private Double amount;
    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate date;
    @Column(columnDefinition="ENUM('COMPLETED','FAILED')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;
    @ManyToOne
    private AppUser participant;
    @ManyToOne
    private Event event;

}
