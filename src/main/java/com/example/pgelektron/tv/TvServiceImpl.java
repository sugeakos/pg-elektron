package com.example.pgelektron.tv;

import com.example.pgelektron.customer.CustomerService;
import com.example.pgelektron.person.Person;
import com.example.pgelektron.person.PersonService;
import com.example.pgelektron.tvcategory.TVCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TvServiceImpl implements TvService {
    private final TvRepository tvRepository;
    private final CustomerService customerService;
    private final PersonService personService;

    @Override
    public TV saveTv(TV tv){
        return tvRepository.save(tv);
    }

    @Override
    public TV getTvById(Long id){
        return tvRepository.getById(id);
    }

    @Override
    public TV createNewTv(Person person, TVCategory tvCategoryId, String errorSeenByCustomer, LocalDateTime reservedDateToRepair){
        TV newTv = new TV();
        newTv.setPerson(person);
        newTv.setTvCategoryId(tvCategoryId);
        newTv.setErrorSeenByCustomer(errorSeenByCustomer);
        newTv.setReservedDateToRepair(reservedDateToRepair);
        return tvRepository.save(newTv);
    }

    @Override
    public List<TV> findAllTvs() {
        List<TV> tvList = new ArrayList<>();
        tvRepository.findAll().forEach(tvList::add);
        return tvList;
    }
}