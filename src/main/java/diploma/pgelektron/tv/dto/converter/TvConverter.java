package diploma.pgelektron.tv.dto.converter;

import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.person.service.PersonService;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TvConverter {
    private final PersonConverter personConverter;
    private final TvCategoryConverter tvCategoryConverter;
    private final TvCategoryService tvCategoryService;
    private final PersonService personService;


    public TvEntity convertDtoToEntity(TvDto dto) {
        if (dto == null) {
            return null;
        }

        TvEntity entity = new TvEntity();
        entity.setExternalId(dto.getExternalId());
        entity.setPersonEntity(personService.findPersonByEmail(dto.getPersonEmail()));
        entity.setTvCategoryEntityId(tvCategoryService.getTvByDescription(dto.getTvCategoryDescription()));
        entity.setErrorSeenByCustomer(dto.getErrorSeenByCustomer());
        entity.setReservedDateToRepair(dto.getReservedDateToRepair());
        entity.setDateOfCorrection(dto.getDateOfCorrection());
        entity.setRepairedError(dto.getRepairedError());
        entity.setPrice(dto.getPrice());
        entity.setItStillInProgress(dto.isItStillInProgress());
        return entity;
    }

    public TvDto convertEntityToDto(TvEntity entity) {
        if (entity == null) {
            return null;
        }
        TvDto dto = new TvDto();
        dto.setExternalId(entity.getExternalId());
        dto.setPersonEmail(entity.getPersonEntity().getEmail());
        dto.setTvCategoryDescription(tvCategoryService.findCategoryDescription(entity.getExternalId()));
        dto.setErrorSeenByCustomer(entity.getErrorSeenByCustomer());
        dto.setReservedDateToRepair(entity.getReservedDateToRepair());
        dto.setDateOfCorrection(entity.getDateOfCorrection());
        dto.setRepairedError(entity.getRepairedError());
        dto.setPrice(entity.getPrice());
        dto.setItStillInProgress(entity.isItStillInProgress());
        return dto;
    }
}
