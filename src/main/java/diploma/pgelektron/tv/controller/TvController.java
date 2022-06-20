package diploma.pgelektron.tv.controller;

import diploma.pgelektron.domain.HttpResponse;
import diploma.pgelektron.tv.dto.converter.TvConverter;
import diploma.pgelektron.tv.dto.domain.TvDto;
import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tv.service.TvService;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import diploma.pgelektron.tvcategory.service.TvCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static diploma.pgelektron.constant.TvConstant.DATE_ALREADY_TAKEN;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/tv")
@RequiredArgsConstructor
@Slf4j
public class TvController {
    private final TvService tvService;
    private final TvCategoryService tvCategoryService;
    private final TvConverter tvConverter;

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('tv:create')")
    public ResponseEntity<TvDto> createNewTv(@RequestBody TvDto tvDto
//            @RequestParam("personEmail") String personEmail,
//            @RequestParam("tvCategoryDescription") String tvCategoryDescription,
//            @RequestParam("errorSeenByCustomer") String errorSeenByCustomer,
//            @RequestParam("reservedDateToRepair") String reservedDateToRepair
    ) throws ParseException {
        List<TvEntity> reservedDateResults = tvService.findTvByReservedDateToRepair(tvDto.getReservedDateToRepair());
        if (reservedDateResults.isEmpty()) {
            TvDto newDto = tvService.createNewTv(tvDto);
            return new ResponseEntity<>(newDto, OK);
        } else {
            response(BAD_REQUEST, DATE_ALREADY_TAKEN);
            return null;
        }
    }

    @PostMapping("/update-reserved-date")
    @PreAuthorize("hasAnyAuthority('tv:create')")
    public ResponseEntity<TvDto> updateReservedDate(@RequestBody TvDto tvDto) throws ParseException {
        List<TvEntity> reservedDateResults = tvService.findTvByReservedDateToRepair(tvDto.getReservedDateToRepair());
        if (reservedDateResults.isEmpty()) {

            TvDto newDto = tvService.updateTvReservedDate(tvDto);
            return new ResponseEntity<>(newDto, OK);
        } else {
            response(BAD_REQUEST, DATE_ALREADY_TAKEN);
            return null;
        }
    }

    @GetMapping("/find/{externalId}")
    @PreAuthorize("hasAnyAuthority('tv:create')")
    public ResponseEntity<TvDto> findTv(@PathVariable("externalId") UUID externalId) {
        TvEntity result = tvService.getTvById(externalId);
        TvDto returnDto = tvConverter.convertEntityToDto(result);
        returnDto.setTvCategoryDescription(result.getTvCategoryEntityId().getDescription());
        return new ResponseEntity<>(returnDto, OK);
    }

    @GetMapping("/{personEmail}")
    public ResponseEntity<List<TvDto>> getAllTvByPerson(@PathVariable("personEmail") String personEmail) {
        List<TvDto> getTvs = tvService.findAllTvsByPersonEmail(personEmail);
        return new ResponseEntity<>(getTvs, OK);
    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @GetMapping("/all-tv")
    public ResponseEntity<List<TvDto>> getAllTv() {
        List<TvDto> tvDtos = tvService.listAllTv();
        return new ResponseEntity<>(tvDtos, OK);
    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @PostMapping("/update")
    public ResponseEntity<TvDto> updateTv(@RequestBody TvDto tvDto) throws ParseException {
        TvDto returnDto = tvService.updateTv(tvDto);
        return new ResponseEntity<>(returnDto, OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()),
                httpStatus);
    }
}
