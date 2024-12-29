package pmk.artamonova.rgr.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Event {

    private UUID id;

    private String eventName;

    private String eventDescription;

    private LocalDate eventDate;

    private UUID historyId;
}
