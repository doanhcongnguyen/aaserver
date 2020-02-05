package com.dcn.aaserver.domain.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Data
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String fullName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private String language;

    @Column
    private Long isDeleted;

    @Column
    @CreationTimestamp
    private Timestamp createdTime;

    @Column
    @CreationTimestamp
    private Timestamp updatedTime;

}
