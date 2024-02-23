package otmankarim.U5W3D5.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otmankarim.U5W3D5.user.User;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    @Column(name = "places_available")
    private int placesAvailable;
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

    public Event(String title, String description, LocalDate date, String location, int placesAvailable, User organizer) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.placesAvailable = placesAvailable;
        this.organizer = organizer;
    }
}
