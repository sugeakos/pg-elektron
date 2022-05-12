package diploma.pgelektron.tv;

import diploma.pgelektron.Person.Person;
import diploma.pgelektron.tvcategory.TvCategory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TvService {
    Tv saveTv(Tv tv);
    Tv getTvById(Long id);
    Tv createNewTv(Person person, TvCategory tvCategoryId, String errorSeenByCustomer, Date reservedDateToRepair);
    List<Tv> findAllTvs();
}
