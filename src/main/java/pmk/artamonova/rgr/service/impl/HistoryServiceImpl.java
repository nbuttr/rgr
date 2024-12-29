package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.component.Extractor;
import pmk.artamonova.rgr.model.History;
import pmk.artamonova.rgr.service.HistoryService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<History> findAll() {
        return jdbcTemplate
                .query("select * from history", new BeanPropertyRowMapper<>(History.class));
    }

    @Override
    public History findById(UUID id) {
        return jdbcTemplate.query(
                "SELECT history.*, e.* FROM history " +
                        "LEFT JOIN event e ON e.history_id = history.id " +
                        "WHERE history.id = ?",
                new Object[]{id}, new Extractor.HistoryExtractor());
    }

    @Override
    public void create(History history) {
        jdbcTemplate
                .update("INSERT INTO history(id, subject_id) VALUES (?,?)",
                UUID.randomUUID(), history.getSubjectId());
    }

    @Override
    public void update(History history) {
        jdbcTemplate
                .update("UPDATE history SET subject_id=? Where id=?",
                history.getSubjectId(), history.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM history WHERE id=?", id);

    }
}
