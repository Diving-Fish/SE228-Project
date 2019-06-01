package com.diving_fish.ebook.ServiceImpl;

import com.diving_fish.ebook.Entity.BookEntity;
import com.diving_fish.ebook.Repository.BookRepository;
import com.diving_fish.ebook.Service.BookManageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookManageServiceImpl implements BookManageService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public JSONObject getBook(Long isbn) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn);
        if (bookEntity == null) {
            return null;
        }
        return bookEntity.to_json();
    }

    @Override
    public JSONArray getBookList() {
        JSONArray jsonArray = new JSONArray();
        List<BookEntity> lb = bookRepository.findAll();
        for (BookEntity e : lb) {
            jsonArray.add(e.to_json());
        }
        return jsonArray;
    }

    @Override
    public void modifyBook(JSONObject book) {
        Long isbn = book.getLong("isbn");
        BookEntity bookEntity = new BookEntity(
                isbn,
                book.get("title").toString(),
                Double.parseDouble(book.get("price").toString()),
                book.get("author").toString(),
                book.get("intro").toString(),
                Integer.parseInt(book.get("stock").toString()),
                Integer.parseInt(book.get("category").toString()),
                true,
                book.getString("imagelink")
        );
        BookEntity be = bookRepository.findByIsbn(isbn);
        if (be != null) {
            bookRepository.deleteByIsbn(isbn);
        }
        bookRepository.save(bookEntity);
    }

    @Override
    public JSONObject removeBook(Long isbn) {
        JSONObject jsonObject = new JSONObject();
        try {
            bookRepository.deleteById(isbn);
            jsonObject.put("status", "200");
            jsonObject.put("message", "delete successfully");
        } catch (EmptyResultDataAccessException e) {
            jsonObject.put("status", "error");
            jsonObject.put("message", "the book with isbn " + isbn + " not exists.");
        }
        return jsonObject;
    }
}
