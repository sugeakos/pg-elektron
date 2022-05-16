package diploma.pgelektron.tvcategory.implementation;

import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.repository.TvCategoryRepository;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TvCategoryServiceImpl implements TvCategoryService {
    private final TvCategoryRepository tvCategoryRepository;
    private final TvCategoryConverter tvCategoryConverter;
    private  final ModelMapper mapper;

    @Override
    public TvCategoryEntity saveTvCategory(TvCategoryEntity category) {
        category.setExternalId(generateNewExternalId());
        log.info(category.getExternalId().toString());
        return tvCategoryRepository.save(category);
    }

    @Override
    public TvCategoryEntity getTvCategoryById(Long id){
        return tvCategoryRepository.getById(id);
    }

    @Override
    public List<TvCategoryEntity> getAllTvCategories() {
        return new ArrayList<>(tvCategoryRepository.findAll());
    }

    @Override
    public TvCategoryEntity getTvByDescription(String description) {
        return tvCategoryRepository.findTvCategoryEntityByDescription(description);
    }

    @Override
    public List<TvCategoryDto> getAllTvCategoryDtos() {
        List<TvCategoryEntity> allCategories = getAllTvCategories();
        List<TvCategoryDto> categoriesDto = allCategories.stream()
                .map(category -> mapper.map(category, TvCategoryDto.class)).collect(Collectors.toList());
        //categoriesDto;
        return categoriesDto;
    }

    @Override
    public TvCategoryDto saveTvCategoryDto(TvCategoryDto tvCategoryDto) {
        TvCategoryEntity entity = tvCategoryConverter.convertDtoToEntity(tvCategoryDto);
        entity.setExternalId(generateNewExternalId());
        TvCategoryEntity savedEntity = tvCategoryRepository.save(entity);

        return tvCategoryConverter.convertEntityToDto(savedEntity);
    }

    public UUID generateNewExternalId(){
       return UUID.randomUUID();
    }
}
