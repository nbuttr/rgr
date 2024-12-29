package pmk.artamonova.rgr.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Producer {

    private UUID id;

    private String country;

    private LocalDate yearOfFoundation;

    private String producerName;

    private List<Subject> subjects = new ArrayList<>();

}
