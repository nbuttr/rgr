package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Owner;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    List<Owner> findAll();

    Owner findById(UUID id);

    void create(Owner owner);

    void update(Owner owner);

    void delete(UUID id);
}
