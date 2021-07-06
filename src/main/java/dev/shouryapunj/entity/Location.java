package dev.shouryapunj.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @Column(name = "LocationId", nullable = false)
    private String locationId;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Zip", nullable = false)
    private String zip;

    @Column(name = "City", nullable = false)
    private String city;

    @Column(name = "State", nullable = false)
    private String state;

    @Column(name = "Country", nullable = false)
    private String country;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn" ,nullable = false)
    private ZonedDateTime modifiedOn;

    public Location(String address, String zip, String city, String state, String country, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.locationId = UUID.randomUUID().toString();
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
