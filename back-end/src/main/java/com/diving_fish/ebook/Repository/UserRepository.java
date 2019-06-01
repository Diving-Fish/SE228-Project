package com.diving_fish.ebook.Repository;

import com.diving_fish.ebook.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, Long> {
    UserEntity findById(Integer id);
    UserEntity findByUsername(String username);
}
