package diploma.pgelektron.tvcategory.controller;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import diploma.pgelektron.tvcategory.dto.converter.TvCategoryConverter;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.implementation.TvCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TvCategoryController {
    private final TvCategoryServiceImpl tvCategoryService;
    private final TvCategoryConverter tvCategoryConverter;
    private final ModelMapper mapper;


    @GetMapping("/tv-categories")
    public ResponseEntity<List<TvCategoryDto>> getAllTvCategories() {
        List<TvCategoryDto> categories = tvCategoryService.getAllTvCategoryDtos();

        return new ResponseEntity<>(categories, OK);

    }

    @PostMapping("/tv-categories/new")
    public ResponseEntity<TvCategoryDto> createNewTvCategory(@RequestBody TvCategoryDto tvCategoryDto) {
        return new ResponseEntity<>(tvCategoryService.saveTvCategoryDto(tvCategoryDto), OK);
    }
}
