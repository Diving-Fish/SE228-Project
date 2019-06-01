package com.diving_fish.ebook.Repository;

import com.diving_fish.ebook.Entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookEntity, Long> {
    BookEntity findByIsbn(Long isbn);
    void deleteByIsbn(Long isbn);
}
