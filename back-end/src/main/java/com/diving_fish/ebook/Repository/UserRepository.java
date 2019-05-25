package com.diving_fish.ebook.Repository;

import com.diving_fish.ebook.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(Integer id);
    UserEntity findByUsername(String username);
}
