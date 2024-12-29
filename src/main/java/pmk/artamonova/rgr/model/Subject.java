package pmk.artamonova.rgr.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Subject {

    private UUID id;

    private String subjectName;

    private String description;

    private LocalDate yearOfProduction;

    private UUID ownerId;

    private UUID producerId;

    List<Sale> sales = new ArrayList<>();

    List<History> histories = new ArrayList<>();
}
