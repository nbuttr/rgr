package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.component.Extractor;
import pmk.artamonova.rgr.model.Subject;
import pmk.artamonova.rgr.service.SubjectService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate
                .query("SELECT * FROM subject", new BeanPropertyRowMapper<>(Subject.class));
    }

    @Override
    public Subject findById(UUID id) {
        return jdbcTemplate.query("SELECT " +
                "subject.id AS subject_id, " +
                "subject.description AS subject_description, " +
                "subject.subject_name AS subject_name," +
                "subject.year_of_production AS subject_year_of_production, " +
                "subject.owner_id AS subject_owner_id, " +
                "subject.producer_id AS subject_producer_id, " +

                "s.id AS sale_id, " +
                "s.auction_id AS sale_auction_id, " +
                "s.buyer_id AS sale_buyer_id, " +
                "s.owner_id AS sale_owner_id, " +
                "s.date_of_sell AS sale_date_of_sell, " +
                "s.place_of_sell AS sale_place_of_sell, " +

                "h.id AS history_id, " +
                "h.subject_id AS history_subject_id, " +

                "e.id AS event_id, " +
                "e.event_date AS event_date, " +
                "e.event_name AS event_name, " +
                "e.event_description AS event_description " +

                "FROM subject " +
                "LEFT JOIN sale s ON s.subject_id = subject.id " +
                "LEFT JOIN history h ON h.subject_id = subject.id " +
                "LEFT JOIN event e ON h.id = e.history_id " +
                "WHERE subject.id = ?",

        new Object[]{id}, new Extractor.SubjectExtractor());
    }

    @Override
    public void create(Subject subject) {
        jdbcTemplate
                .update("INSERT INTO subject(id, subject_name, description, year_of_production, owner_id, producer_id) VALUES(?,?,?,?,?,?)",
                UUID.randomUUID(),subject.getSubjectName(),subject.getDescription(),subject.getYearOfProduction(),subject.getOwnerId(),subject.getProducerId());

    }

    @Override
    public void update(Subject subject) {
        jdbcTemplate
                .update("UPDATE subject SET subject_name=?, description=?, year_of_production=?, owner_id=?, producer_id=? WHERE id=?",
                subject.getSubjectName(),subject.getDescription(),subject.getYearOfProduction(),subject.getOwnerId(),subject.getProducerId(),subject.getId());

    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate
                .update("DELETE FROM subject WHERE id=?", id);

    }
}
