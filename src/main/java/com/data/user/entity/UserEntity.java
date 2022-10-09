package com.data.user.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table
public class UserEntity {
    @Id
    @Column(name = "userId")
    private String id;

    @Column
    private Timestamp createdOn;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String emailId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
