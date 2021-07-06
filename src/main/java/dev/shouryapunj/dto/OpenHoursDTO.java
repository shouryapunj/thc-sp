package dev.shouryapunj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OpenHoursDTO {

    private String openHoursId;

    private String dateOfOperation;

    private LocalDateTime startsAt;

    private LocalDateTime closesAt;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;
}
