package dev.shouryapunj.entity;

import lombok.*;

import javax.persistence.*;
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

//    @ManyToOne
//    @JoinColumn(name="orderId", insertable = false, updatable = false)
//    private OrderCart orderCart;

    public Item(Menu menu, String quantity, String orderId) {
        this.itemId = UUID.randomUUID().toString();
        this.menu = menu;
        this.quantity = quantity;
        this.orderId = orderId;
    }
}
