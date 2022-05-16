package diploma.pgelektron.tv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tv")
@Transactional
public class TvEntity {
    @Id
    @SequenceGenerator(name = "tv_sequence", sequenceName = "tv_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_sequence")
    private Long id;

    private UUID externalId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    private PersonEntity personEntity;

    @OneToOne
    @JoinColumn(nullable = false, name = "tv_category_id")
    private TvCategoryEntity tvCategoryEntityId;

    @Column(nullable = false)
    private String errorSeenByCustomer;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date reservedDateToRepair;

    @Column(nullable = true)
    private String repairedError;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date dateOfCorrection;

    @Column(nullable = true)
    private Long price;

    private boolean isItStillInProgress;

}
