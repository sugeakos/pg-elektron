package diploma.pgelektron.tv.service;

import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TvService {
    TvDto saveTv(TvDto dto);
    TvDto getTvById(UUID id);
    TvDto createNewTv(String email, String tvCategoryDescription, String errorSeenByCustomer, Date reservedDateToRepair);
    List<TvDto> findAllTvsByPersonEmail(String personEmail);
    List<TvDto> listAllTv();
}
