package dev.shouryapunj.controller;

import dev.shouryapunj.dto.ItemDTO;
import dev.shouryapunj.entity.OrderCart;
import dev.shouryapunj.service.ApiStatsService;
import dev.shouryapunj.service.OrderCartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordercart")
public class OrderCartController {

    private static final Logger logger = LogManager.getLogger(OrderCartController.class);

    private final OrderCartService orderCartService;

    private final ApiStatsService apiStatsService;

    public OrderCartController(OrderCartService orderCartService, ApiStatsService apiStatsService) {
        this.orderCartService = orderCartService;
        this.apiStatsService = apiStatsService;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getAllOrders() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all orders");
        Optional<List<OrderCart>> allOrders = orderCartService.getAllOrderCart();
        if (allOrders.isEmpty()) {
            logger.info("Fetched 0 Orders!");
            apiStatsService.saveApiStatistics("/api/ordercart/get", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No data present for Orders!");
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + allOrders.get().size() + " Orders in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/ordercart/get", RequestMethod.GET, end-start);
        return ResponseEntity.ok(allOrders.get());
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addOrderCart(@RequestBody List<ItemDTO> itemList) {
        long start = System.currentTimeMillis();
        logger.info("Adding new Order");
        Optional<OrderCart> orderCart = orderCartService.addNewOrder(itemList);
        if (orderCart.isEmpty()) {
            logger.info("Couldn't place the Order!");
            apiStatsService.saveApiStatistics("/api/ordercart/add", RequestMethod.POST, System.currentTimeMillis()-start);
            return ResponseEntity.ok("Order not placed!");
        }
        long end = System.currentTimeMillis();
        logger.info("Added new Order " + orderCart.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/ordercart/add", RequestMethod.POST, end-start);
        return ResponseEntity.ok(orderCart.get());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrderById(@PathVariable("id") String orderId) {
        long start = System.currentTimeMillis();
        logger.info("Fetching Order");
        Optional<OrderCart> order = orderCartService.getOrderById(orderId);
        if (order.isEmpty()) {
            logger.info("Fetched 0 Orders!");
            apiStatsService.saveApiStatistics("/api/ordercart/get/{id}", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No data present for Order with orderId : "+ orderId);
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched 1 Order in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/ordercart/get/{id}", RequestMethod.GET, end-start);
        return ResponseEntity.ok(order.get());
    }

    @RequestMapping(value = "/get/{from}/{to}/{sortBy}", method = RequestMethod.GET)
    public ResponseEntity getOrderById(@PathVariable("from") String from, @PathVariable("id") String to, @PathVariable("sortBy") String sortBy) {
        long start = System.currentTimeMillis();
        logger.info("Fetching Order");
        Optional<List<OrderCart>> order = orderCartService.getOrdersByPageAndSortBy(Integer.parseInt(from), Integer.parseInt(to), sortBy);
        if (order.isEmpty()) {
            logger.info("Fetched 0 Orders!");
            apiStatsService.saveApiStatistics("/api/ordercart/get/{from}/{to}/{sortBy}", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No data present for Order");
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched 1 Order in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/ordercart/get/{from}/{to}/{sortBy}", RequestMethod.GET, end-start);
        return ResponseEntity.ok(order.get());
    }

}
