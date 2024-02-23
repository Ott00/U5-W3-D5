package otmankarim.U5W3D5.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.U5W3D5.exceptions.EventException;
import otmankarim.U5W3D5.exceptions.NotFoundException;
import otmankarim.U5W3D5.exceptions.UnauthorizedException;
import otmankarim.U5W3D5.user.User;
import otmankarim.U5W3D5.user.UserDAO;
import otmankarim.U5W3D5.user.UserService;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    public Page<Event> getEvents(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return eventDAO.findAll(pageable);
    }

    public Event save(EventDTO newEvent, User user) {
        Event event = new Event(
                newEvent.title(),
                newEvent.description(),
                newEvent.date(),
                newEvent.location(),
                newEvent.placesAvailable(),
                user
        );
        return eventDAO.save(event);
    }

    public Event findById(long id) {
        return eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event findByIdAndUpdate(long id, EventDTO updateEvent, User user) {
        Event found = this.findById(id);
        //Controllo che l'organizzatore dell'evento sia la stessa persona che cerca di modificarlo
        if (found.getOrganizer().getId().equals(user.getId())) {
            found.setTitle(updateEvent.title());
            found.setDescription(updateEvent.description());
            found.setDate(updateEvent.date());
            found.setLocation(updateEvent.location());
            found.setPlacesAvailable(updateEvent.placesAvailable());
            return eventDAO.save(found);
        } else throw new UnauthorizedException("This event is organized by another user");
    }

    public void findByIdAndDelete(long id, User user) {
        Event found = this.findById(id);
        if (found.getOrganizer().getId().equals(user.getId())) {
            eventDAO.delete(found);
        } else throw new UnauthorizedException("This event is organized by another user");
    }

    public void addReservation(long id, User user) {
        Event found = findById(id);
        if (found.getParticipants().size() < found.getPlacesAvailable()) {
            found.getParticipants().add(user);
            eventDAO.save(found);
        } else throw new EventException("Event is soldout, check another one!");
    }
}











