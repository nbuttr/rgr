package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Auction;

import java.util.List;
import java.util.UUID;

public interface AuctionService {

    List<Auction> findAll();

    Auction findById(UUID id);

    void create(Auction auction);

    void update(Auction auction);

    void delete(UUID id);
}
