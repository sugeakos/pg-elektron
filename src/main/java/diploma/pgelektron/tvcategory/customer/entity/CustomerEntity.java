package diploma.pgelektron.tvcategory.customer.entity;

import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.tv.entity.TvEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    private Long id;

    private UUID externalId;

    @OneToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    private PersonEntity person_Entity_id;

    @OneToMany(mappedBy = "personEntity")
    private List<TvEntity> tvEntities = new ArrayList<>();

}