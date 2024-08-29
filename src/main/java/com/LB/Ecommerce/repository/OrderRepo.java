package com.LB.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LB.Ecommerce.entity.Order;


public interface OrderRepo extends JpaRepository<Order, Long> {
}