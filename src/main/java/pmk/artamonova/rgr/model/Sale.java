package pmk.artamonova.rgr.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Sale {

    private UUID id;

    private UUID ownerId;

    private UUID buyerId;

    private LocalDate dateOfSell;

    private String placeOfSell;

    private UUID subjectId;

    private UUID auctionId;
}
