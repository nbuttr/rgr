package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.component.Extractor;
import pmk.artamonova.rgr.model.Owner;
import pmk.artamonova.rgr.service.OwnerService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Owner> findAll() {
        return jdbcTemplate
                .query("SELECT * FROM owner", new BeanPropertyRowMapper<>(Owner.class));
    }

    @Override
    public Owner findById(UUID id) {
        return jdbcTemplate.query("""
                SELECT 
                    owner.*, 
                    s.id AS subject_id, s.subject_name, s.description, s.year_of_production, s.producer_id,
                    so.id AS sale_owner_id, so.subject_id AS sale_subject_id, so.auction_id, so.date_of_sell, so.place_of_sell, so.buyer_id AS sale_buyer_id,
                    sb.id AS sale_buyer_id, sb.subject_id AS sale_subject_id_buyer, sb.auction_id AS auction_id_buyer, sb.date_of_sell AS date_of_sell_buyer, sb.place_of_sell AS place_of_sell_buyer, sb.owner_id AS sale_owner_id_buyer
                FROM owner 
                LEFT JOIN subject s ON s.owner_id = owner.id 
                LEFT JOIN sale so ON so.owner_id = owner.id 
                LEFT JOIN sale sb ON sb.buyer_id = owner.id 
                WHERE owner.id = ?
                """,
                new Object[]{id}, new Extractor.OwnerExtractor());
    }

    @Override
    public void create(Owner owner) {
        jdbcTemplate.update("INSERT INTO owner(id, first_name, last_name, address, mobile_number) VALUES(?,?,?,?,?)",
                UUID.randomUUID(), owner.getFirstName(), owner.getLastName(), owner.getAddress(), owner.getMobileNumber());
    }

    @Override
    public void update(Owner owner) {
        jdbcTemplate.update("UPDATE owner SET first_name=?, last_name=?, address=?, mobile_number=? WHERE id=?",
                owner.getFirstName(), owner.getLastName(), owner.getAddress(),owner.getMobileNumber(), owner.getId());

    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM owner WHERE id=?", id);
    }
}
