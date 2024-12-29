package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Producer;

import java.util.List;
import java.util.UUID;

public interface ProducerService {

    List<Producer> findAll();

    Producer findById(UUID id);

    void create(Producer auction);

    void update(Producer auction);

    void delete(UUID id);
}
