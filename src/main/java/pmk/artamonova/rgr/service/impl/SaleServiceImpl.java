package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.model.Sale;
import pmk.artamonova.rgr.service.SaleService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Sale> findAll() {
        return jdbcTemplate
                .query("SELECT * FROM sale", new BeanPropertyRowMapper<>(Sale.class));
    }

    @Override
    public Sale findById(UUID id) {
        return jdbcTemplate
                .query("SELECT * FROM sale WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Sale.class))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Sale sale) {
        jdbcTemplate
                .update("INSERT INTO sale(id, owner_id, buyer_id, date_of_sell, place_of_sell, subject_id, auction_id)" +
                        " VALUES(?,?,?,?,?,?,?)",
                        UUID.randomUUID(),
                        sale.getOwnerId(),
                        sale.getBuyerId(),
                        sale.getDateOfSell(),
                        sale.getPlaceOfSell(),
                        sale.getSubjectId(),
                        sale.getAuctionId());
    }

    @Override
    public void update(Sale sale) {
        jdbcTemplate.
                update("UPDATE sale SET owner_id=?, buyer_id=?, date_of_sell=?, place_of_sell=?, subject_id=?, auction_id=? WHERE id=?",
                        sale.getOwnerId(),
                        sale.getBuyerId(),
                        sale.getDateOfSell(),
                        sale.getPlaceOfSell(),
                        sale.getSubjectId(),
                        sale.getAuctionId(),
                        sale.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM sale WHERE id=?", id);
    }
}
