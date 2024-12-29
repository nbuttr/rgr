--liquibase formatted SQL

-- changeset artamonova:30
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE subject (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         subject_name VARCHAR(255) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         year_of_production DATE NOT NULL,
                         owner_id UUID NOT NULL,
                         producer_id UUID NOT NULL
);

CREATE TABLE owner (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255),
                         address VARCHAR(255) NOT NULL,
                         mobile_number VARCHAR(255) NOT NULL

);


CREATE TABLE producer (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            country VARCHAR(255) NOT NULL,
                            year_of_foundation DATE NOT NULL,
                            producer_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE history (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           subject_id UUID NOT NULL
);


CREATE TABLE sale (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        owner_id UUID NOT NULL ,
                        buyer_id UUID NOT NULL,
                        date_of_sell DATE NOT NULL,
                        place_of_sell VARCHAR(255) NOT NULL,
                        subject_id UUID NOT NULL,
                        auction_id uuid NOT NULL
);

CREATE TABLE auction (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         auction_date DATE NOT NULL
);


CREATE TABLE event (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       event_name VARCHAR(255) NOT NULL,
                       event_description VARCHAR(255) NOT NULL,
                       event_date DATE NOT NULL,
                       history_id UUID NOT NULL
);

CREATE UNIQUE INDEX unique_owner_id ON sale(owner_id);

ALTER TABLE event
    ADD FOREIGN KEY(history_id) REFERENCES history(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE history
    ADD FOREIGN KEY(subject_id) REFERENCES subject(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE subject
    ADD FOREIGN KEY("owner_id") REFERENCES owner(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE sale
    ADD FOREIGN KEY(auction_id) REFERENCES auction(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE sale
    ADD FOREIGN KEY(buyer_id) REFERENCES owner(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE subject
    ADD FOREIGN KEY(producer_id) REFERENCES producer(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE sale
    ADD FOREIGN KEY(subject_id) REFERENCES subject(id)
        ON UPDATE NO ACTION ON DELETE CASCADE;