package pmk.artamonova.rgr.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class History {

    private UUID id;

    private UUID subjectId;

    private List<Event> events;
}
