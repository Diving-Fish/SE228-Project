package com.diving_fish.ebook.entity;

import javax.persistence.*;

@Entity
@Table(name = "book")
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

    public String to_json() {
        String ret = "{";
        ret += "\"isbn\": " + isbn + ", ";
        ret += "\"title\": \"" + title + "\", ";
        ret += "\"price\": " + price + ", ";
        ret += "\"author\": \"" + author + "\", ";
        ret += "\"intro\": \"" + intro + "\", ";
        ret += "\"stock\": " + stock + ", ";
        ret += "\"category\": " + category + ", ";
        ret += "\"valid\": " + valid + ", ";
        ret += "}";
        return ret;
    }

    public void set(Long _isbn, String _title, Double _price, String _author, String _intro, Integer _stock, Integer _category, Boolean _valid) {
        isbn = _isbn;
        title = _title;
        price = _price;
        author = _author;
        intro = _intro;
        stock = _stock;
        category = _category;
        valid = _valid;
    }
}
