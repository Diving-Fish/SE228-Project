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

    public String to_json() {
        String ret = "{";
        ret += "\"isbn\": " + isbn + ", ";
        ret += "\"title\": \"" + title + "\", ";
        ret += "\"price\": " + price + ", ";
        ret += "\"author\": \"" + author + "\", ";
        ret += "\"intro\": \"" + intro + "\", ";
        ret += "\"stock\": " + stock + ", ";
        ret += "\"category\": " + category + ", ";
        ret += "}";
        return ret;
    }
}
