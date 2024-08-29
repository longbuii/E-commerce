package com.LB.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LB.Ecommerce.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
