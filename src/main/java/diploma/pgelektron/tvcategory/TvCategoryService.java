package diploma.pgelektron.tvcategory;

import java.util.List;

public interface TvCategoryService {
    TvCategory saveTvCategory(TvCategory category);
    TvCategory getTvCategoryById(Long id);
    TvCategory getTvByDescription(String description);
    List<TvCategory> getAllTvCategories();
}
