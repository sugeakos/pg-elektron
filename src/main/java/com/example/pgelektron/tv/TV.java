package com.example.pgelektron.tv;

import com.example.pgelektron.customer.Customer;
import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TV {
    @Id
    @SequenceGenerator(name = "tv_sequence", sequenceName = "tv_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_sequence")
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    private Person person;

    @OneToOne
    @JoinColumn(nullable = false, name = "tv_category_id")
    private TVCategory tvCategoryId;

    private String errorSeenByCustomer;
    private LocalDateTime reservedDateToRepair;


    private String repairedError;
    private LocalDateTime dateOfCorrection;
}
