package pmk.artamonova.rgr.component;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import pmk.artamonova.rgr.model.Auction;
import pmk.artamonova.rgr.model.Event;
import pmk.artamonova.rgr.model.History;
import pmk.artamonova.rgr.model.Owner;
import pmk.artamonova.rgr.model.Producer;
import pmk.artamonova.rgr.model.Sale;
import pmk.artamonova.rgr.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class Extractor {

    public static class AuctionExtractor implements ResultSetExtractor<Auction>{

        @Override
        public Auction extractData(ResultSet rs) throws SQLException, DataAccessException {
            Auction auction = new Auction();

            List<Sale> saleList = new ArrayList<>();

            while (rs.next()) {
                if(auction.getId() == null) {
                    auction.setId(rs.getObject("id", UUID.class));
                    auction.setAuctionDate((rs.getObject("auction_date", LocalDate.class)));
                    auction.setSales(new ArrayList<>());
                }

                Sale sa = new Sale();
                sa.setId(rs.getObject("id", UUID.class));
                sa.setAuctionId(rs.getObject("auction_id", UUID.class));
                sa.setBuyerId(rs.getObject("buyer_id", UUID.class));
                sa.setOwnerId(rs.getObject("owner_id", UUID.class));
                sa.setDateOfSell(rs.getObject("date_of_sell", LocalDate.class));
                sa.setPlaceOfSell(rs.getObject("place_of_sell", String.class));
                sa.setSubjectId(rs.getObject("subject_id", UUID.class));
                saleList.add(sa);
            }
            auction.setSales(saleList);
            return auction;
        }
    }

    public static class SubjectExtractor implements ResultSetExtractor<Subject> {

        @Override
        public Subject extractData(ResultSet rs) throws SQLException, DataAccessException {
            Subject subject = null;
            Map<UUID, Sale> saleMap = new HashMap<>();
            Map<UUID, History> historyMap = new HashMap<>();

            while (rs.next()) {

                if (subject == null) {
                    subject = new Subject();
                    subject.setId(rs.getObject("subject_id", UUID.class));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setSubjectName(rs.getString("subject_name"));
                    subject.setYearOfProduction(rs.getObject("subject_year_of_production", LocalDate.class));
                    subject.setOwnerId(rs.getObject("subject_owner_id", UUID.class));
                    subject.setProducerId(rs.getObject("subject_producer_id", UUID.class));
                    subject.setSales(new ArrayList<>());
                    subject.setHistories(new ArrayList<>());
                }

                UUID saleId = rs.getObject("sale_id", UUID.class);
                if (saleId != null && !saleMap.containsKey(saleId)) {
                    Sale sale = new Sale();
                    sale.setId(saleId);
                    sale.setAuctionId(rs.getObject("sale_auction_id", UUID.class));
                    sale.setBuyerId(rs.getObject("sale_buyer_id", UUID.class));
                    sale.setOwnerId(rs.getObject("sale_owner_id", UUID.class));
                    sale.setDateOfSell(rs.getObject("sale_date_of_sell", LocalDate.class));
                    sale.setPlaceOfSell(rs.getString("sale_place_of_sell"));
                    sale.setSubjectId(rs.getObject("subject_id", UUID.class));
                    saleMap.put(saleId, sale);
                }

                UUID historyId = rs.getObject("history_id", UUID.class);
                if (historyId != null) {
                    History history = historyMap.getOrDefault(historyId, new History());
                    history.setId(historyId);
                    history.setSubjectId(rs.getObject("history_subject_id", UUID.class));

                    UUID eventId = rs.getObject("event_id", UUID.class);
                    if (eventId != null) {
                        Event event = new Event();
                        event.setId(eventId);
                        event.setEventDate(rs.getObject("event_date", LocalDate.class));
                        event.setEventName(rs.getString("event_name"));
                        event.setEventDescription(rs.getString("event_description"));

                        if (history.getEvents() == null) {
                            history.setEvents(new ArrayList<>());
                        }
                        history.getEvents().add(event);
                    }

                    historyMap.put(historyId, history);
                }
            }

            if (subject != null) {
                subject.setSales(new ArrayList<>(saleMap.values()));
                subject.setHistories(new ArrayList<>(historyMap.values()));
            }

            return subject;
        }
    }


    public static class HistoryExtractor implements ResultSetExtractor<History>{

        @Override
        public History extractData(ResultSet rs) throws SQLException, DataAccessException {
            History history = new History();

            List<Event> events = new ArrayList<>();


            while (rs.next()) {
                if(history.getId() == null) {
                    history.setId(rs.getObject("id", UUID.class));
                    history.setSubjectId(rs.getObject("subject_id", UUID.class));
                }

                Event ev = new Event();
                ev.setId(rs.getObject("id", UUID.class));
                ev.setEventDate(rs.getObject("event_date", LocalDate.class));
                ev.setEventName(rs.getObject("event_name", String.class));
                ev.setEventDescription(rs.getObject("event_description", String.class));
                events.add(ev);
            }
            history.setEvents(events);
            return history;
        }
    }

    public static class ProducerExtractor implements ResultSetExtractor<Producer>{

        @Override
        public Producer extractData(ResultSet rs) throws SQLException, DataAccessException {
            Producer producer = new Producer();

            List<Subject> subjectList = new ArrayList<>();

            while (rs.next()) {
                if(producer.getId() == null) {
                    producer.setId(rs.getObject("id", UUID.class));
                    producer.setProducerName((rs.getObject("producer_name", String.class)));
                    producer.setCountry(rs.getObject("country", String.class));
                    producer.setYearOfFoundation(rs.getObject("year_of_foundation", LocalDate.class));
                    producer.setSubjects(new ArrayList<>());
                }

                Subject sb = new Subject();
                sb.setId(rs.getObject("id", UUID.class));
                sb.setSubjectName(rs.getObject("subject_name", String.class));
                sb.setDescription(rs.getObject("description", String.class));
                sb.setYearOfProduction(rs.getObject("year_of_production", LocalDate.class));
                sb.setOwnerId(rs.getObject("owner_id", UUID.class));
                sb.setProducerId(rs.getObject("producer_id", UUID.class));
                subjectList.add(sb);
            }
            producer.setSubjects(subjectList);
            return producer;
        }
    }

    public static class OwnerExtractor implements ResultSetExtractor<Owner> {

        @Override
        public Owner extractData(ResultSet rs) throws SQLException, DataAccessException {
            Owner owner = null;
            Map<UUID, Subject> subjectMap = new HashMap<>();
            Map<UUID, Sale> salesAsOwner = new HashMap<>();
            Map<UUID, Sale> salesAsBuyer = new HashMap<>();

            while (rs.next()) {
                if (owner == null) {
                    owner = new Owner();
                    owner.setId(rs.getObject("id", UUID.class));
                    owner.setFirstName(rs.getString("first_name"));
                    owner.setLastName(rs.getString("last_name"));
                    owner.setAddress(rs.getString("address"));
                    owner.setMobileNumber(rs.getString("mobile_number"));
                }

                UUID subjectId = rs.getObject("subject_id", UUID.class);
                if (subjectId != null && !subjectMap.containsKey(subjectId)) {
                    Subject subject = new Subject();
                    subject.setId(subjectId);
                    subject.setSubjectName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("description"));
                    subject.setYearOfProduction(rs.getObject("year_of_production", LocalDate.class));
                    subject.setProducerId(rs.getObject("producer_id", UUID.class));
                    subjectMap.put(subjectId, subject);
                }

                UUID saleOwnerId = rs.getObject("sale_owner_id", UUID.class);
                if (saleOwnerId != null && !salesAsOwner.containsKey(saleOwnerId)) {
                    Sale sale = new Sale();
                    sale.setId(saleOwnerId);
                    sale.setSubjectId(rs.getObject("sale_subject_id", UUID.class));
                    sale.setAuctionId(rs.getObject("auction_id", UUID.class));
                    sale.setDateOfSell(rs.getObject("date_of_sell", LocalDate.class));
                    sale.setPlaceOfSell(rs.getString("place_of_sell"));
                    sale.setBuyerId(rs.getObject("sale_buyer_id", UUID.class));
                    salesAsOwner.put(saleOwnerId, sale);
                }

                UUID saleBuyerId = rs.getObject("sale_buyer_id", UUID.class);
                if (saleBuyerId != null && !salesAsBuyer.containsKey(saleBuyerId)) {
                    Sale sale = new Sale();
                    sale.setId(saleBuyerId);
                    sale.setSubjectId(rs.getObject("sale_subject_id_buyer", UUID.class));
                    sale.setAuctionId(rs.getObject("auction_id_buyer", UUID.class));
                    sale.setDateOfSell(rs.getObject("date_of_sell_buyer", LocalDate.class));
                    sale.setPlaceOfSell(rs.getString("place_of_sell_buyer"));
                    sale.setOwnerId(rs.getObject("sale_owner_id_buyer", UUID.class));
                    salesAsBuyer.put(saleBuyerId, sale);
                }
            }

            if (owner != null) {
                owner.setSubjects(new ArrayList<>(subjectMap.values()));
                owner.setOwnerSales(new ArrayList<>(salesAsOwner.values()));
                owner.setBuyerSales(new ArrayList<>(salesAsBuyer.values()));
            }

            return owner;
        }
    }

}
