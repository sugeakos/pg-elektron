package com.example.pgelektron.createtv;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateNewTvRequest {
    private Person personId;
    private TVCategory tvCategoryId;
    private String errorSeenByCustomer;
    private LocalDateTime reservedDateToRepair;
}
