package pmk.artamonova.rgr.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pmk.artamonova.rgr.component.Extractor;
import pmk.artamonova.rgr.model.Auction;
import pmk.artamonova.rgr.service.AuctionService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Auction> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM auction", new BeanPropertyRowMapper<>(Auction.class));
    }

    @Override
    public void create(Auction auction) {
        jdbcTemplate.update("INSERT INTO auction(id, auction_date)" + "VALUES(?,?)",
                UUID.randomUUID(), auction.getAuctionDate());
    }

    @Override
    public void update(Auction auction) {
        jdbcTemplate.update("UPDATE auction SET auction_date=? WHERE id=?"
            , auction.getAuctionDate(), auction.getId());
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM auction WHERE id=?", id);

    }

    @Override
    public Auction findById(UUID id) {
        return jdbcTemplate.query(
                    "SELECT auction.*, s.* FROM auction " +
                        "LEFT JOIN sale s ON s.auction_id = auction.id " +
                        "WHERE auction.id = ?",
                new Object[]{id}, new Extractor.AuctionExtractor());

    }
}
