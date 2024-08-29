package com.LB.Ecommerce.service.interf;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import com.LB.Ecommerce.dto.OrderRequest;
import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.enums.OrderStatus;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
