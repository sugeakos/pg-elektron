package diploma.pgelektron.tvcategory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TvCategoryController {
    private final TvCategoryServiceImpl tvCategoryService;

    public TvCategoryController(TvCategoryServiceImpl tvCategoryService) {
        this.tvCategoryService = tvCategoryService;
    }

    @GetMapping("/tv-categories")
    public String getAllTvCategories(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<TvCategory> categories = tvCategoryService.getAllTvCategories();
        log.error("Request cumo: " + request.getHeader(AUTHORIZATION));
        model.addAttribute("categories", categories);
        return "tvcategories/listTvCategories";

    }

    @PostMapping("/tv-categories/new")
    public ResponseEntity<TvCategory> createNewTvCategory(@RequestBody TvCategory tvCategory) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/tv-categories/new").toUriString());
        return ResponseEntity.created(uri).body( tvCategoryService.saveTvCategory(tvCategory));
    }
}
