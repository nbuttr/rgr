package pmk.artamonova.rgr.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Owner {

    private UUID id;

    private String firstName;

    private String lastName;

    private String address;

    private String mobileNumber;

    private List<Subject> subjects;

    private List<Sale> buyerSales;

    private List<Sale> ownerSales;
}
