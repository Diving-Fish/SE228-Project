package com.diving_fish.ebook.repository;

import com.diving_fish.ebook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(Integer id);
    UserEntity findByUsername(String username);
}
