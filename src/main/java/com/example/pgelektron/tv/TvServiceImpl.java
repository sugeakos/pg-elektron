package com.example.pgelektron.tv;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TvServiceImpl implements  TvService{
    private final TvRepository tvRepository;

    @Override
    public TV saveTv(TV tv){
        return tvRepository.save(tv);
    }

    @Override
    public TV getTvById(Long id){
        return tvRepository.getById(id);
    }

    @Override
    public TV createNewTv(Person personId, TVCategory tvCategoryId, String errorSeenByCustomer, LocalDateTime reservedDateToRepair){
        TV newTv = new TV();
        newTv.setPerson(personId);
        newTv.setTvCategoryId(tvCategoryId);
        newTv.setErrorSeenByCustomer(errorSeenByCustomer);
        newTv.setReservedDateToRepair(reservedDateToRepair);
        return tvRepository.save(newTv);
    }

}
