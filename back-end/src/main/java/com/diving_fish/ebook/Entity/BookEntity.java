package com.diving_fish.ebook.Entity;

import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection = "book")
@Data
public class BookEntity {
    @Id
    public Long isbn;
    @Column(length = 32)
    public String title;
    @Column(length = 8)
    public Double price;
    @Column
    public String author;
    @Column
    public String intro;
    @Column
    public Integer stock;
    @Column
    public Integer category;
    @Column
    public Boolean valid;
    @Column
    public String imagelink;

    public JSONObject to_json() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isbn", isbn);
        jsonObject.put("title", title);
        jsonObject.put("price", price);
        jsonObject.put("author", author);
        jsonObject.put("intro", intro);
        jsonObject.put("stock", stock);
        jsonObject.put("category", category);
        jsonObject.put("valid", valid);
        jsonObject.put("imagelink", imagelink);
        return jsonObject;
    }

    public BookEntity() {

    }

    public BookEntity(Long _isbn, String _title, Double _price, String _author, String _intro, Integer _stock, Integer _category, Boolean _valid, String _imagelink) {
        isbn = _isbn;
        title = _title;
        price = _price;
        author = _author;
        intro = _intro;
        stock = _stock;
        category = _category;
        valid = _valid;
        imagelink = _imagelink;
    }
}
