package diploma.pgelektron.tv.controller;

import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tv.service.TvService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/tv")
@RequiredArgsConstructor
public class TvController {
    private final TvService tvService;

    @PostMapping("/new")
    public ResponseEntity<TvDto> createNewTv(@RequestParam("personExternalId") UUID personExternalId,
                                             @RequestParam("tvCategoryDescription") String tvCategoryDescription,
                                             @RequestParam("errorSeenByCustomer") String errorSeenByCustomer
//                                             @RequestParam("reservedDateToRepair")Date reservedDateToRepair
                                             ) {
        TvDto newDto =  tvService.createNewTv(personExternalId, tvCategoryDescription,
                errorSeenByCustomer, new Date());
        return new ResponseEntity<>(newDto, OK);
    }

    @GetMapping("/{personExternalId}")
    public ResponseEntity<List<TvDto>> getAllTvByPerson(@PathVariable("personExternalId") String personExternalId) {
        List<TvDto> getTvs = tvService.findAllTvsByPersonExternalId(personExternalId);
        return new ResponseEntity<>(getTvs, OK);
    }

}
