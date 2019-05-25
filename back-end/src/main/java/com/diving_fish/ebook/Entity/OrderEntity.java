package com.diving_fish.ebook.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
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
    @CreatedDate
    private Timestamp create_date;
    @Column
    @LastModifiedDate
    private Timestamp modify_date;

    public OrderEntity() {

    }

    public OrderEntity(Integer id, Integer groupid, Integer userid, Long isbn, Integer amount, Timestamp create_date, Timestamp modify_date) {
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

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getModify_date() {
        return modify_date;
    }

    public void setModify_date(Timestamp modify_date) {
        this.modify_date = modify_date;
    }
}
