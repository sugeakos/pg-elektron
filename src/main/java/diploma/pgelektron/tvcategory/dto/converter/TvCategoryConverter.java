package diploma.pgelektron.tvcategory.dto.converter;

import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TvCategoryConverter {
    private final ModelMapper mapper;
    public TvCategoryEntity convertDtoToEntity (TvCategoryDto tvCategoryDto) {

        if(tvCategoryDto == null) {
            return null;
        }

        TvCategoryEntity entity = new TvCategoryEntity();
        entity.setExternalId(tvCategoryDto.getExternalId());
        entity.setDescription(tvCategoryDto.getDescription());
        return entity;
    }

    public TvCategoryDto convertEntityToDto (TvCategoryEntity tvCategoryEntity) {

        if(tvCategoryEntity == null) {
            return null;
        }

        TvCategoryDto dto = new TvCategoryDto();
        dto.setExternalId(tvCategoryEntity.getExternalId());
        dto.setDescription(tvCategoryEntity.getDescription());
        return dto;
    }
}
