package emp.event_management_platform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true)
    @NotEmpty
    private  String username;
    @NotEmpty
    private  String firstname;
    @NotEmpty
    private  String lastname;
    @NotNull
    private  String address;
    @NotNull
    private  String country;
    @NotNull
    private  String gender;

    private  String password;
    @NotNull
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles ;
    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private List<Event> events;
    @ManyToMany(mappedBy = "waitinglist", fetch = FetchType.EAGER)
    private List<Event> waitingEvents;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<AppPayment> payments ;
}
