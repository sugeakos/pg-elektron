package com.example.pgelektron.tv;

import com.example.pgelektron.customer.Customer;
import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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

    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    private Person person;

    @OneToOne
    @JoinColumn(nullable = false, name = "tv_category_id")
    private TVCategory tvCategoryId;

    private String errorSeenByCustomer;
    private LocalDateTime reservedDateToRepair;

    private String repairedError;
    private LocalDateTime dateOfCorrection;

    private boolean isItStillInProgress = true;

}
