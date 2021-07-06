package dev.shouryapunj.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class LocationDTO {

    private String id;

    private String address;

    private String zip;

    private String city;

    private String state;

    private String country;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;

}
