package pmk.artamonova.rgr.service;

import pmk.artamonova.rgr.model.Subject;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    List<Subject> findAll();

    Subject findById(UUID id);

    void create(Subject subject);

    void update(Subject subject);

    void delete(UUID id);
}
