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
public class ItemDTO {

    private String itemId;

    private String menuId;

    private String quantity;

    private String locationId;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;

    public ItemDTO(String menuId, String quantity, String locationId, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.locationId = locationId;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
