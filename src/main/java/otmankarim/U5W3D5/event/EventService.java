package otmankarim.U5W3D5.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.U5W3D5.exceptions.NotFoundException;
import otmankarim.U5W3D5.user.User;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;

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

    public Event findByIdAndUpdate(long id, EventDTO updateEvent) {
        Event found = this.findById(id);
        found.setTitle(updateEvent.title());
        found.setDescription(updateEvent.description());
        found.setDate(updateEvent.date());
        found.setLocation(updateEvent.location());
        found.setPlacesAvailable(updateEvent.placesAvailable());
        return eventDAO.save(found);
    }

    public void findByIdAndDelete(long id) {
        Event found = this.findById(id);
        eventDAO.delete(found);
    }
}
