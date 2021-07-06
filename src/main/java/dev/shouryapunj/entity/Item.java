package dev.shouryapunj.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @Column(name = "ItemId", nullable = false)
    private String itemId;

    @OneToOne
    @JoinColumn(name = "MenuId")
    private Menu menu;

    @Column(name = "Quantity", nullable = false)
    private String quantity;

    @Column(name = "orderId", nullable = false)
    private String orderId;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    public Item(Menu menu, String quantity, String orderId, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.itemId = UUID.randomUUID().toString();
        this.menu = menu;
        this.quantity = quantity;
        this.orderId = orderId;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
