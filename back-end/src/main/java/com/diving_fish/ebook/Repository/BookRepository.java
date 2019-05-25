package com.diving_fish.ebook.Repository;

import com.diving_fish.ebook.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    BookEntity findByIsbn(Long isbn);
}
