package diploma.pgelektron.tv;

import diploma.pgelektron.Person.Person;
import diploma.pgelektron.Person.PersonService;
import diploma.pgelektron.customer.CustomerService;
import diploma.pgelektron.tvcategory.TvCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class TvServiceImpl implements TvService {

    private final TvRepository tvRepository;
    private final CustomerService customerService;
    private final PersonService personService;

    @Override
    public Tv saveTv(Tv tv){
        return tvRepository.save(tv);
    }

    @Override
    public Tv getTvById(Long id){
        return tvRepository.getById(id);
    }

    @Override
    public Tv createNewTv(Person person, TvCategory tvCategoryId, String errorSeenByCustomer, Date reservedDateToRepair){
        Tv newTv = new Tv();
        newTv.setPerson(person);
        newTv.setTvCategoryId(tvCategoryId);
        newTv.setErrorSeenByCustomer(errorSeenByCustomer);
        newTv.setReservedDateToRepair(reservedDateToRepair);
        return tvRepository.save(newTv);
    }

    @Override
    public List<Tv> findAllTvs() {
        List<Tv> tvList = new ArrayList<>();
        tvRepository.findAll().forEach(tvList::add);
        return tvList;
    }

}
