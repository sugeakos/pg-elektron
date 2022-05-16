package diploma.pgelektron.tvcategory.dto.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Component
public class TvCategoryDto {

    private UUID externalId;

    private String description;
}
