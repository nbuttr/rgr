package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.component.Extractor;
import pmk.artamonova.rgr.model.Producer;
import pmk.artamonova.rgr.service.ProducerService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Producer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM producer", new BeanPropertyRowMapper<>(Producer.class));
    }

    @Override
    public void create(Producer producer) {
        jdbcTemplate.update("INSERT INTO producer(id, country, year_of_foundation, producer_name)" + "VALUES(?,?,?,?)",
                UUID.randomUUID(), producer.getCountry(), producer.getYearOfFoundation(), producer.getProducerName());
    }

    @Override
    public void update(Producer producer) {
        jdbcTemplate.update("UPDATE producer SET country=?, producer_name=?, year_of_foundation=? WHERE id=?",
                producer.getCountry(), producer.getProducerName(), producer.getYearOfFoundation(), producer.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM producer WHERE id=?", id);
    }

    @Override
    public Producer findById(UUID id) {
        return jdbcTemplate.query(
                "SELECT producer.*, s.* FROM producer " +
                        "LEFT JOIN subject s ON s.producer_id = producer.id " +
                        "WHERE producer.id = ?",
                new Object[]{id}, new Extractor.ProducerExtractor());

    }
}
