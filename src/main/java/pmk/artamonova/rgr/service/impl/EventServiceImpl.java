package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.model.Event;
import pmk.artamonova.rgr.service.EventService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findAll() {
        return jdbcTemplate
                .query("select * from event", new BeanPropertyRowMapper<>(Event.class));
    }

    @Override
    public Event findById(UUID id) {
        return jdbcTemplate
                .query("SELECT * FROM event WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Event.class))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Event event) {
        jdbcTemplate
                .update("INSERT INTO event(id, event_name, event_description, event_date, history_id) VALUES(?,?,?,?,?)",
                UUID.randomUUID(), event.getEventName(), event.getEventDescription(), event.getEventDate(), event.getHistoryId());
    }

    @Override
    public void update(Event event) {
        jdbcTemplate
                .update("UPDATE event SET event_name=?, event_description=?, event_date=?, history_id=? Where id=?",
                event.getEventName(), event.getEventDescription(), event.getEventDate(),event.getHistoryId(), event.getId());

    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM event WHERE id=?", id);
    }
}
