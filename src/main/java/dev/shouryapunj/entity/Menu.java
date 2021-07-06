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
@Table(name = "MENU")
public class Menu {

    @Id
    @Column(name = "MenuId", nullable = false)
    private String menuId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "UnitPrice", nullable = false)
    private double unitPrice;

    @Column(name = "CreatedOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "ModifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    public Menu(String name, double unitPrice, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.menuId = UUID.randomUUID().toString();
        this.name = name;
        this.unitPrice = unitPrice;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public Menu(String menuId) {
        this.menuId = menuId;
    }
}
