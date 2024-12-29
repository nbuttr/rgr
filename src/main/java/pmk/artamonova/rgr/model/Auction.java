package pmk.artamonova.rgr.model;

import lombok.Data;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Auction {

    private UUID id;

    private List<Sale> sales;

    private LocalDate auctionDate;

}
