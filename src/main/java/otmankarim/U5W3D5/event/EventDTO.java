package otmankarim.U5W3D5.event;

import java.time.LocalDate;

public record EventDTO(
        String title,
        String description,
        LocalDate date,
        String location,
        int placesAvailable

) {
}
