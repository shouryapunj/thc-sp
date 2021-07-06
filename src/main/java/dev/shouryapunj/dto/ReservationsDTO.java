package dev.shouryapunj.dto;

import dev.shouryapunj.entity.Location;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class ReservationsDTO {

    private String reservationId;

    private String name;

    private String email;

    private LocalDateTime dateTime;

    private String numberOfPeople;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;

    private Location location;
}
