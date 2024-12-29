package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.History;

import java.util.List;
import java.util.UUID;

public interface HistoryService {

    List<History> findAll();

    History findById(UUID id);

    void create(History history);

    void update(History history);

    void delete(UUID id);
}
