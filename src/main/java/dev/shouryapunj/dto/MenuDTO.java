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
public class MenuDTO {

    private String id;

    private String name;

    private double unitPrice;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;
}
