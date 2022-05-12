package diploma.pgelektron.tvcategory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class TvCategoryServiceImpl implements TvCategoryService {
    private final TvCategoryRepository tvCategoryRepository;

    @Override
    public TvCategory saveTvCategory(TvCategory category) {
        return tvCategoryRepository.save(category);
    }

    @Override
    public TvCategory getTvCategoryById(Long id){
        return tvCategoryRepository.getById(id);
    }

//    public TVCategory createNewTvCategory(TVCategory tvCategory){
//
//    }

    @Override
    public List<TvCategory> getAllTvCategories() {
        List<TvCategory> categories = new ArrayList<>();
        tvCategoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public TvCategory getTvByDescription(String description) {
        return null;
    }
}
