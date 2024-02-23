package otmankarim.U5W3D5.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import otmankarim.U5W3D5.user.User;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy) {
        return this.eventService.getEvents(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable long id) {
        return this.eventService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Event save(@RequestBody EventDTO newEvent, @AuthenticationPrincipal User user) {
        return this.eventService.save(newEvent, user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event findByIdAndUpdate(@PathVariable int id, @RequestBody EventDTO updateEvent, @AuthenticationPrincipal User user) {
        return this.eventService.findByIdAndUpdate(id, updateEvent, user);
    }

    @PatchMapping("/{id}/addReservation")
    @PreAuthorize("hasAuthority('NORMAL')")
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public void addReservation(@PathVariable int id, @AuthenticationPrincipal User user) {
        this.eventService.addReservation(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable long id, @AuthenticationPrincipal User user) {
        this.eventService.findByIdAndDelete(id, user);
    }
}
