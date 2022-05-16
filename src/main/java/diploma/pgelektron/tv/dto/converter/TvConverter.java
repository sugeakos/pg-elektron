package diploma.pgelektron.tv.dto.converter;

import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TvConverter {
    private final PersonConverter personConverter;
    private final TvCategoryConverter tvCategoryConverter;

    public TvEntity convertDtoToEntity(TvDto dto) {
        if (dto == null) {
            return null;
        }

        TvEntity entity = new TvEntity();
        entity.setExternalId(dto.getExternalId());
        entity.setPersonEntity(personConverter.convertDtoToEntity(dto.getPersonDto()));
        entity.setTvCategoryEntityId(tvCategoryConverter.convertDtoToEntity(dto.getTvCategoryDto()));
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
        dto.setPersonDto(personConverter.convertEntityToDto(entity.getPersonEntity()));
        dto.setTvCategoryDto(tvCategoryConverter.convertEntityToDto(entity.getTvCategoryEntityId()));
        dto.setErrorSeenByCustomer(entity.getErrorSeenByCustomer());
        dto.setReservedDateToRepair(entity.getReservedDateToRepair());
        dto.setDateOfCorrection(entity.getDateOfCorrection());
        dto.setRepairedError(entity.getRepairedError());
        dto.setPrice(entity.getPrice());
        dto.setItStillInProgress(entity.isItStillInProgress());
        return dto;
    }
}
