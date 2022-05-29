package diploma.pgelektron.tv.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import diploma.pgelektron.person.dto.domain.PersonDto;

import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Component
public class TvDto {
    private UUID externalId;

    private String personEmail;
    private String tvCategoryDescription;

    private String errorSeenByCustomer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date reservedDateToRepair;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date dateOfCorrection;
    private String repairedError;

    private Long price;
    private boolean isItStillInProgress;


}
