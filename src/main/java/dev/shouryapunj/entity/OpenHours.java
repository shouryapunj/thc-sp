package dev.shouryapunj.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "OPENHOURS")
public class OpenHours {

    @Id
    @Column(name = "OpenHoursId", nullable = false)
    private String openHoursId;

    @Column(name = "DateOfOperation", nullable = false, unique = true)
    private String dateOfOperation;

    @Column(name = "StartsAt", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ClosesAt", nullable = false)
    private LocalDateTime closesAt;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    public OpenHours(String dateOfOperation, LocalDateTime startsAt, LocalDateTime closesAt, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.openHoursId = UUID.randomUUID().toString();
        this.dateOfOperation = dateOfOperation;
        this.startsAt = startsAt;
        this.closesAt = closesAt;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
