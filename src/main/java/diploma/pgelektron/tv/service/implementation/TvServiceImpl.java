package diploma.pgelektron.tv.service.implementation;

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
    public TvDto getTvById(UUID id) {
        TvEntity entity = tvRepository.findTvEntityByExternalId(id);
        return tvConverter.convertEntityToDto(entity);
    }



    @Override
    public TvDto createNewTv(String email, String tvCategoryDescription,
                             String errorSeenByCustomer, String reservedDateToRepair) throws ParseException {

        log.info("DATUM STRING: " + reservedDateToRepair);
        Date newFormattedDate = DateUtils.parseDate(reservedDateToRepair,new String[] {"yyyy-MM-dd"});
        log.info("DATUM: " + newFormattedDate);
        TvDto dto = new TvDto();
        dto.setExternalId(generateNewExternalId());
        dto.setPersonEmail(email);
        dto.setTvCategoryDescription(tvCategoryDescription);
        dto.setErrorSeenByCustomer(errorSeenByCustomer);
        dto.setReservedDateToRepair(newFormattedDate);

        TvEntity savedEntity = tvConverter.convertDtoToEntity(dto);
        savedEntity.setItStillInProgress(true);
        savedEntity.setPersonEntity(personService.findPersonByEmail(email));
        savedEntity.setTvCategoryEntityId(tvCategoryService.getTvByDescription(tvCategoryDescription));
        tvRepository.save(savedEntity);
        return dto;
    }

    @Override
    public List<TvDto> findAllTvsByPersonEmail(String personEmail) {
        List<TvEntity> findTvs = tvRepository.findAllTvByPersonEmail(personEmail);
        return findTvs.stream().map(entity -> mapper.map(entity,TvDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TvDto> listAllTv() {
        List<TvEntity> findTvs = tvRepository.findAll();
        return findTvs.stream().map(entity -> mapper.map(entity,TvDto.class)).collect(Collectors.toList());
    }

    @Override
    public TvDto updateTv(UUID externalTvId, String repairedError, String price) throws ParseException {
        TvEntity entity = tvRepository.findTvEntityByExternalId(externalTvId);
        Long newPrice = Long.parseLong(price);
        entity.setRepairedError(repairedError);
        entity.setDateOfCorrection(new Date());
        entity.setPrice(newPrice);
        entity.setItStillInProgress(false);
        tvRepository.save(entity);
        return tvConverter.convertEntityToDto(entity);
    }

    @Override
    public TvDto updateTvReservedDate(UUID externalId, String reservedDateToRepair) throws ParseException {
        Date newDate = DateUtils.parseDate(reservedDateToRepair,"yyyy-MM-dd");
        TvEntity entity = tvRepository.findTvEntityByExternalId(externalId);
        entity.setReservedDateToRepair(newDate);
        tvRepository.save(entity);
        return tvConverter.convertEntityToDto(entity);
    }

    private UUID generateNewExternalId(){
        return UUID.randomUUID();
    }
}
