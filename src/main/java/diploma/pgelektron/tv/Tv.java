package diploma.pgelektron.tv;

import com.fasterxml.jackson.annotation.JsonFormat;
import diploma.pgelektron.Person.Person;
import diploma.pgelektron.tvcategory.TvCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tv {
    @Id
    @SequenceGenerator(name = "tv_sequence", sequenceName = "tv_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_person_id")
    @NotNull
    @NotEmpty
    @NotBlank
    private Person person;

    @OneToOne
    @JoinColumn(nullable = false, name = "tv_category_id")
    private TvCategory tvCategoryId;

    @Column(nullable = false)
    private String errorSeenByCustomer;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    @FutureOrPresent
    private Date reservedDateToRepair;

    @Column(nullable = true)
    private String repairedError;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date dateOfCorrection;

    @Column(nullable = true)
    private Long price;

    private boolean isItStillInProgress = true;
}
