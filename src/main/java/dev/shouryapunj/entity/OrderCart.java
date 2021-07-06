package dev.shouryapunj.entity;

import dev.shouryapunj.constants.OrderStatus;
import dev.shouryapunj.dto.ItemDTO;
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
@Table(name = "ORDERCART")
public class OrderCart {

    @Id
    @Column(name = "OrderId", nullable = false)
    private String orderId;

    @OneToMany(mappedBy = "itemId")
    @Column
    private List<Item> items;

    @Column(name = "locationId", nullable = false)
    private String locationId;

    @Column(name = "Total", nullable = false)
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "OrderStatus", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    public OrderCart(List<Item> items, double total, String locationId, OrderStatus orderStatus, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.total = total;
        this.locationId = locationId;
        this.orderStatus = orderStatus;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
