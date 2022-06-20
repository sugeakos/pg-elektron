package diploma.pgelektron.tv.service.implementation;

import diploma.pgelektron.domain.HttpResponse;
import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.person.service.PersonService;
import diploma.pgelektron.tv.dto.converter.TvConverter;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.repository.TvRepository;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tv.service.TvService;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import static diploma.pgelektron.constant.TvConstant.DATE_ALREADY_TAKEN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
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
    public TvEntity getTvById(UUID id) {

        return tvRepository.findTvByExternalId(id);
    }


    @Override
    public TvDto createNewTv(TvDto tvDto) throws ParseException {
        //tvDto.setExternalId(generateNewExternalId());

        TvEntity savedEntity = tvConverter.convertDtoToEntity(tvDto);
        savedEntity.setItStillInProgress(true);
        savedEntity.setPersonEntity(personService.findPersonByEmail(tvDto.getPersonEmail()));
        savedEntity.setTvCategoryEntityId(tvCategoryService.getTvByDescription(tvDto.getTvCategoryDescription()));
        savedEntity.setExternalId(generateNewExternalId());
        savedEntity.setPrice(null);
        tvRepository.save(savedEntity);
        return tvDto;


    }

    @Override
    public List<TvDto> findAllTvsByPersonEmail(String personEmail) {
        List<TvEntity> findTvs = tvRepository.findAllTvByPersonEmail(personEmail);
        return findTvs.stream().map(entity -> mapper.map(entity, TvDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TvDto> listAllTv() {
        List<TvEntity> findTvs = tvRepository.findAll();
        return findTvs.stream().map(entity -> mapper.map(entity, TvDto.class)).collect(Collectors.toList());
    }

    @Override
    public TvDto updateTv(TvDto tvDto) throws ParseException {
        TvEntity entity = tvRepository.findTvEntityByExternalId(tvDto.getExternalId());
        entity.setRepairedError(tvDto.getRepairedError());
        entity.setDateOfCorrection(new Date());
        entity.setPrice(tvDto.getPrice());
        entity.setItStillInProgress(false);
        tvRepository.save(entity);
        return tvConverter.convertEntityToDto(entity);
    }

    @Override
    public TvDto updateTvReservedDate(TvDto tvDto) throws ParseException {
        TvEntity entity = tvRepository.findTvEntityByExternalId(tvDto.getExternalId());
        entity.setReservedDateToRepair(tvDto.getReservedDateToRepair());
        tvRepository.save(entity);
        return tvConverter.convertEntityToDto(entity);

    }

    @Override
    public List<TvEntity> findTvByReservedDateToRepair(Date reservedDateToRepair) {
        return tvRepository.findTvEntitiesByReservedDateToRepair(reservedDateToRepair);
    }

    private UUID generateNewExternalId() {
        return UUID.randomUUID();
    }
}
