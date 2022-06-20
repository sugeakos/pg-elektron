package diploma.pgelektron.tv.service;

import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TvService {
    TvDto saveTv(TvDto dto);
    TvEntity getTvById(UUID id);
    //TvDto createNewTv(String email, String tvCategoryDescription, String errorSeenByCustomer, String reservedDateToRepair) throws ParseException;
    TvDto createNewTv(TvDto tvDto) throws ParseException;

    List<TvDto> findAllTvsByPersonEmail(String personEmail);
    List<TvDto> listAllTv();
    TvDto updateTv(TvDto tvDto) throws ParseException;
    TvDto updateTvReservedDate(TvDto tvDto) throws ParseException;
    List<TvEntity> findTvByReservedDateToRepair(Date reservedDateToRepair);
}
