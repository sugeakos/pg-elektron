package diploma.pgelektron.tvcategory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tv_category")
public class TvCategoryEntity {
    @Id
    @SequenceGenerator(name = "tv_category_sequence", sequenceName = "tv_category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_category_sequence")
    private Long id;

    private UUID externalId;

    @NotEmpty
    @NotBlank
    @Unique
    private String description;
}
