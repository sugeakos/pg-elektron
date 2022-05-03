package com.example.pgelektron.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person implements Serializable {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    private String username;

    private String password;
    private String phoneFix;
    private String phoneMobile;

    @Column(name = "address")
    private String addressLine;


    private String profileImageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date lastLoginDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date lastLoginDateDisplay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date joinDate;

    private String role; //ROLE_USER, ROLE_ADMIN
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

    //private Collection<TV> tvs = new ArrayList<>();



    public Person(String firstName,
                  String lastName,
                  String email,
                  String username,
                  String password,
                  String phoneFix,
                  String phoneMobile,
                  String addressLine,
                  String profileImageUrl,
                  Date lastLoginDate,
                  Date lastLoginDateDisplay,
                  Date joinDate,
                  String role,
                  String[] authorities,
                  boolean isActive,
                  boolean isNotLocked) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneFix = phoneFix;
        this.phoneMobile = phoneMobile;
        this.addressLine = addressLine;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


}
