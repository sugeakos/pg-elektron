package diploma.pgelektron.tv.controller;

import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tv.service.TvService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
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
    @PreAuthorize("hasAnyAuthority('tv:create')")
    public ResponseEntity<TvDto> createNewTv(@RequestParam("personEmail") String personEmail,
                                             @RequestParam("tvCategoryDescription") String tvCategoryDescription,
                                             @RequestParam("errorSeenByCustomer") String errorSeenByCustomer,
                                             @RequestParam("reservedDateToRepair")String reservedDateToRepair
                                             ) throws ParseException {
        TvDto newDto =  tvService.createNewTv(personEmail, tvCategoryDescription,
                errorSeenByCustomer, reservedDateToRepair);
        return new ResponseEntity<>(newDto, OK);
    }

    @GetMapping("/{personEmail}")
    public ResponseEntity<List<TvDto>> getAllTvByPerson(@PathVariable("personEmail") String personEmail) {
        List<TvDto> getTvs = tvService.findAllTvsByPersonEmail(personEmail);
        return new ResponseEntity<>(getTvs, OK);
    }

    @GetMapping("/all-tv")
    @PreAuthorize("hasAnyAuthority('user:find')")
    public ResponseEntity<List<TvDto>> getAllTv() {
        List<TvDto> tvDtos = tvService.listAllTv();
        return new ResponseEntity<>(tvDtos, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<TvDto> updateTv(@RequestParam("externalTvId") UUID externalTvId,
                                          @RequestParam("errorSeenByCustomer") String errorSeenByCustomer,
                                          @RequestParam(value = "reservedDateToRepair", required = false) String reservedDateToRepair) throws ParseException {
        TvDto returnDto = tvService.updateTv(externalTvId,errorSeenByCustomer,reservedDateToRepair);
        return new ResponseEntity<>(returnDto,OK);
    }
}
