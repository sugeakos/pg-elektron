package com.example.pgelektron.customer;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.tv.TV;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer{
    @Id
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    private Person person_id;

    @OneToOne
    @JoinColumn(nullable = false, name = "customers_tv_id")
    private TV tv_id;

}
