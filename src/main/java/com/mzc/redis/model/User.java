package com.mzc.redis.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


//@Entity
@Table (name = "USERS")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private long id;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "USER_EMAIL_ID")
    private String emailId;
    @Column(name = "USER_PHONE_NO")
    private String phoneNo;

    public User(long id, String username, String emailId, String phoneNo) {
        this.id = id;
        this.username = username;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
    }

    public User() {

    }

}
