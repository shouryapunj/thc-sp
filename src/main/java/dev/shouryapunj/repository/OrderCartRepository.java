package dev.shouryapunj.repository;

import dev.shouryapunj.constants.OrderStatus;
import dev.shouryapunj.entity.OrderCart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface OrderCartRepository extends PagingAndSortingRepository<OrderCart, String> {

    @Modifying
    @Query(
            value = "UPDATE ORDERCART O " +
                    "SET ORDER_STATUS = :orderStatus, " +
                    "MODIFIED_ON = :modifiedOn " +
                    "WHERE O.ORDER_ID = :orderId",
            nativeQuery = true
    )
    void updateOrderById(@Param("orderId") String orderId, @Param("orderStatus") OrderStatus orderStatus, @Param("modifiedOn") ZonedDateTime modifiedOn);

}
