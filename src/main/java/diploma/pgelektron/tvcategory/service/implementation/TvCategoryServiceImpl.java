package diploma.pgelektron.tvcategory.service.implementation;

import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.repository.TvCategoryRepository;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TvCategoryServiceImpl implements TvCategoryService {
    private final TvCategoryRepository tvCategoryRepository;
    private final TvCategoryConverter tvCategoryConverter;
    private final ModelMapper mapper;

    @Override
    public TvCategoryEntity saveTvCategory(TvCategoryEntity category) {
        category.setExternalId(generateNewExternalId());
        log.info(category.getExternalId().toString());
        return tvCategoryRepository.save(category);
    }

    @Override
    public TvCategoryEntity getTvCategoryById(Long id) {
        return tvCategoryRepository.getById(id);
    }

    @Override
    public String findCategoryDescription(UUID externalCategoryId) {
        return tvCategoryRepository.findDescriptionById(externalCategoryId);
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
        List<TvCategoryEntity> allCategories = tvCategoryRepository.findAll(Sort.by("description").ascending());
        return allCategories.stream()
                .map(category -> mapper.map(category, TvCategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public TvCategoryDto saveTvCategoryDto(String description) {
        String newDescription = StringUtils.capitalize(description.toLowerCase());
        List<TvCategoryEntity> allCats = getAllTvCategories();
        if (allCats.contains(findCategoryByDescription(newDescription))) {
            return null;
        } else {

            TvCategoryEntity entity = new TvCategoryEntity();
            entity.setExternalId(generateNewExternalId());
            entity.setDescription(newDescription);
            tvCategoryRepository.save(entity);

            return tvCategoryConverter.convertEntityToDto(entity);
        }
    }

    @Override
    public TvCategoryEntity findCategoryByDescription(String description) {
        return tvCategoryRepository.findTvCategoryEntityByDescription(description);
    }

    @Override
    public String findCatById(Long id) {
        return tvCategoryRepository.findDescripton(id);
    }

    public UUID generateNewExternalId() {
        return UUID.randomUUID();
    }
}
