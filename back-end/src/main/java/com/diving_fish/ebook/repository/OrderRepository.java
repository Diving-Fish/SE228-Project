package com.diving_fish.ebook.repository;

import com.diving_fish.ebook.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findById(Integer id);
    List<OrderEntity> findAllByUserid(Integer userid);
    List<OrderEntity> findAllByGroupid(Integer groupid);
}
