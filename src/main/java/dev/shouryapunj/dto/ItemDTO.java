package dev.shouryapunj.dto;

import dev.shouryapunj.entity.Menu;
import lombok.*;
import org.springframework.stereotype.Component;

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

    public ItemDTO(String menuId, String quantity, String locationId) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.locationId = locationId;
    }
}
