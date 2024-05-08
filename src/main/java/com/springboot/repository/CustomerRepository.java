package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,String> {

}
