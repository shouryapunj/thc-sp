package dev.shouryapunj.repository;

import dev.shouryapunj.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, String> {

    List<Item> findItemByOrderId(String orderId);
}
