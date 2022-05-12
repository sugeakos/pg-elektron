package diploma.pgelektron.tvcategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tv_category")
public class TvCategory {
    @Id
    @SequenceGenerator(name = "tv_category_sequence", sequenceName = "tv_category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_category_sequence")
    private Long id;

    private String description;
}
