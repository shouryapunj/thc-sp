package dev.shouryapunj.service;

import dev.shouryapunj.constants.OrderStatus;
import dev.shouryapunj.dto.ItemDTO;
import dev.shouryapunj.dto.OrderCartDTO;
import dev.shouryapunj.entity.Item;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.entity.Menu;
import dev.shouryapunj.entity.OrderCart;
import dev.shouryapunj.repository.ItemRepository;
import dev.shouryapunj.repository.LocationRepository;
import dev.shouryapunj.repository.MenuRepository;
import dev.shouryapunj.repository.OrderCartRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderCartService {

    private static final Logger logger = LogManager.getLogger(OrderCartService.class);

    private final OrderCartRepository orderCartRepository;

    private final MenuRepository menuRepository;

    private final ItemRepository itemRepository;

    private final LocationRepository locationRepository;

    private final KafkaProducerService kafkaProducerService;

    public OrderCartService(OrderCartRepository orderCartRepository, MenuRepository menuRepository, ItemRepository itemRepository, LocationRepository locationRepository, KafkaProducerService kafkaProducerService) {
        this.orderCartRepository = orderCartRepository;
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Optional<List<OrderCart>> getAllOrderCart() {
        Optional<List<OrderCart>> orderCartList = Optional.of(new ArrayList<>());

        Iterator<OrderCart> iterator = orderCartRepository.findAll().iterator();
        while (iterator.hasNext()) {
            OrderCart orderCart = iterator.next();
            List<Item> itemList = itemRepository.findItemByOrderId(orderCart.getOrderId());
            orderCart.setItems(itemList);
            orderCartList.get().add(orderCart);
        }

        return orderCartList.get().size() == 0 ? Optional.empty() : orderCartList;
    }

    @Transactional
    public Optional<OrderCart> addNewOrder(List<ItemDTO> itemDTOList) {

        OrderCart orderCart = new OrderCart(
                null,
                0,
                null,
                OrderStatus.NEW,
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );

        List<Item> itemList = new ArrayList<>();
        double totalPrice = 0;
        for (ItemDTO itemDTO : itemDTOList) {
            Optional<Menu> menu = menuRepository.findById(itemDTO.getMenuId());
            Optional<Location> location = locationRepository.findById(itemDTO.getLocationId());
            if (menu.isEmpty()) {
                logger.info("No menu item present for menuId :" + itemDTO.getMenuId());
                continue;
            } else if (location.isEmpty()) {
                logger.info("No location present for locationId :" + itemDTO.getLocationId());
                continue;
            }

            totalPrice += menu.get().getUnitPrice() * Double.valueOf(itemDTO.getQuantity());
            Item item = new Item(menu.get(), itemDTO.getQuantity(), orderCart.getOrderId(), ZonedDateTime.now(), ZonedDateTime.now());
            itemRepository.save(item);
            itemList.add(item);
            orderCart.setLocationId(location.get().getLocationId());
        }

        if (totalPrice != 0) {
            orderCart.setItems(itemList);
            orderCart.setTotal(totalPrice);
            orderCartRepository.save(orderCart);
            OrderCartDTO orderCartDTO = new OrderCartDTO(
                    orderCart.getOrderId(),
                    orderCart.getItems(),
                    orderCart.getLocationId(),
                    orderCart.getTotal(),
                    orderCart.getOrderStatus(),
                    orderCart.getCreatedOn(),
                    orderCart.getModifiedOn()
            );
            kafkaProducerService.sendOrder(orderCartDTO);
        } else {
            orderCart = null;
        }

        return orderCart != null ? Optional.of(orderCart) : Optional.empty();
    }

    public Optional<OrderCart> getOrderById(String orderId) {
        Optional<OrderCart> orderCart = orderCartRepository.findById(orderId);
        if (orderCart.isEmpty()) {
            return Optional.empty();
        }
        List<Item> itemList = itemRepository.findItemByOrderId(orderId);
        orderCart.get().setItems(itemList);
        return orderCart;
    }

    @Transactional
    public void consumeOrder(OrderCartDTO orderCart) {
        logger.info("Consuming Order from Kafka");
        orderCart.setOrderStatus(OrderStatus.COMPLETED);
        orderCartRepository.updateOrderById(orderCart.getOrderId(), orderCart.getOrderStatus(), ZonedDateTime.now());
        logger.info("Order completed : " + orderCart.getOrderId());
    }

    public Optional<List<OrderCart>> getOrdersByPageAndSortBy(int from , int to, String sortBy) {
        Optional<List<OrderCart>> orderCartList = Optional.of(orderCartRepository.findAll(PageRequest.of(from, to, Sort.by(sortBy))).toList());

        if (orderCartList.isEmpty()) {
            return Optional.empty();
        }

        return orderCartList;
    }
}
