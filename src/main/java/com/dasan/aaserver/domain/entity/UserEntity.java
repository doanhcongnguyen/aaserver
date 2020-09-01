package com.dasan.aaserver.domain.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Data
public class UserEntity extends AuditingEntity implements Serializable {

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
}
