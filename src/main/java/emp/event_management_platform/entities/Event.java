package emp.event_management_platform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String location;
    @Temporal(TemporalType.DATE)
    @NotNull
    @Future
    private LocalDate date;
    @Min(1)
    @NotNull
    private int capacity;
    @Column(columnDefinition = "ENUM('CONFERENCE','WORKSHOP','PARTY')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType type ;
    @NotNull
    @Min(1)
    private Double price;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppUser> participants;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppUser> waitinglist;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppPayment> payments;
}
