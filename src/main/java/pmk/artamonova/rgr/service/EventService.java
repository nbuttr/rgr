package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {

    List<Event> findAll();

    Event findById(UUID id);

    void create(Event event);

    void update(Event event);

    void delete(UUID id);
}
