package com.diving_fish.ebook.Repository;

import com.diving_fish.ebook.Entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    OrderEntity findById(Integer id);
    List<OrderEntity> findAllByUserid(Integer userid);
    List<OrderEntity> findAllByGroupid(Integer groupid);
}
