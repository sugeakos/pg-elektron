package diploma.pgelektron.customer;

import diploma.pgelektron.Person.Person;
import diploma.pgelektron.tv.Tv;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "person")
    private List<Tv> tvs = new ArrayList<>();

}