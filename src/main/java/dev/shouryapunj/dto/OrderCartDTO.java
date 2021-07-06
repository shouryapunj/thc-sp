package dev.shouryapunj.dto;

import dev.shouryapunj.constants.OrderStatus;
import dev.shouryapunj.entity.Item;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class OrderCartDTO {

    private String orderId;

    private List<Item> items;

    private String locationId;

    private double total;

    private OrderStatus orderStatus;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;

}
