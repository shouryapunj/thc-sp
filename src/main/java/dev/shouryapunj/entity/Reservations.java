package dev.shouryapunj.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RESERVATIONS")
public class Reservations {

    @Id
    @Column(name = "ReservationId", nullable = false)
    private String reservationId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "NumberOfPeople", nullable = false)
    private String numberOfPeople;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Reservations(String name, String email, LocalDateTime dateTime, String numberOfPeople, ZonedDateTime createdOn, ZonedDateTime modifiedOn, Location location) {
        this.reservationId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.location = location;
    }
}
