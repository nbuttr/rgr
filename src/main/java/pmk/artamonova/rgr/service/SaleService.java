package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    List<Sale> findAll();

    Sale findById(UUID id);

    void create(Sale sale);

    void update(Sale sale);

    void delete(UUID id);
}
