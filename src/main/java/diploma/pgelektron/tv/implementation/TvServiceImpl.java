package diploma.pgelektron.tv.implementation;

import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.person.service.PersonService;
import diploma.pgelektron.tv.dto.converter.TvConverter;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tvcategory.customer.service.CustomerService;
import diploma.pgelektron.tv.repository.TvRepository;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tv.service.TvService;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional

public class TvServiceImpl implements TvService {

    private final TvRepository tvRepository;
    private final TvCategoryService tvCategoryService;
    private final PersonConverter personConverter;
    private final TvCategoryConverter tvCategoryConverter;
    private final ModelMapper mapper;
    private final TvConverter tvConverter;
    private final PersonService personService;

    @Override
    public TvDto saveTv(TvDto dto) {
        TvEntity savedEntity = tvConverter.convertDtoToEntity(dto);
        tvRepository.save(savedEntity);
        return dto;
    }

    @Override
    public TvDto getTvById(UUID id) {
        TvEntity entity = tvRepository.findTvEntityByExternalId(id);
        return tvConverter.convertEntityToDto(entity);
    }

    @Override
    public TvDto createNewTv(UUID personExternalId, String tvCategoryDescription,
                             String errorSeenByCustomer, Date reservedDateToRepair) {
        TvDto dto = new TvDto();
        dto.setExternalId(generateNewExternalId());
        dto.setPersonDto(personConverter.convertEntityToDto(personService.findPersonByExternalId(personExternalId)));
        dto.setTvCategoryDto(tvCategoryConverter.convertEntityToDto(tvCategoryService.getTvByDescription(tvCategoryDescription)));
        dto.setErrorSeenByCustomer(errorSeenByCustomer);
        dto.setReservedDateToRepair(reservedDateToRepair);
        TvEntity savedEntity = tvConverter.convertDtoToEntity(dto);
        tvRepository.save(savedEntity);
        return dto;
    }

    @Override
    public List<TvDto> findAllTvsByPersonExternalId(UUID personExternalId) {
        List<TvEntity> findTvs = tvRepository.findAllTvByPersonExternalId(personExternalId);
        return findTvs.stream().map(entity -> mapper.map(entity,TvDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TvDto> listAllTv() {
        List<TvEntity> findTvs = tvRepository.findAll();
        return findTvs.stream().map(entity -> mapper.map(entity,TvDto.class)).collect(Collectors.toList());
    }

    private UUID generateNewExternalId(){
        return UUID.randomUUID();
    }
}
