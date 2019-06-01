package com.diving_fish.ebook.Entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Data
@Document(collection = "order")
public class OrderEntity {
    @Id
    private Integer id;
    @Column
    private Integer groupid;
    @Column
    private Integer userid;
    @Column
    private Long isbn;
    @Column
    private Integer amount;
    @Column
    private Date create_date;
    @Column
    private Date modify_date;

    public OrderEntity() {

    }

    public OrderEntity(Integer id, Integer groupid, Integer userid, Long isbn, Integer amount, Date create_date, Date modify_date) {
        this.id = id;
        this.groupid = groupid;
        this.userid = userid;
        this.isbn = isbn;
        this.amount = amount;
        this.create_date = create_date;
        this.modify_date = modify_date;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupid;
    }

    public void setGroupId(Integer groupid) {
        this.groupid = groupid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
