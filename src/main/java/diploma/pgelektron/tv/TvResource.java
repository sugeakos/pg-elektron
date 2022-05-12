package diploma.pgelektron.tv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/v1/tv")
@RequiredArgsConstructor
public class TvResource {
    private final TvService tvService;

    @GetMapping("/all-tv")
    public ResponseEntity<List<Tv>> getAllTVs(){
        return ResponseEntity.ok().body(tvService.findAllTvs());
    }

    @PostMapping("/new")
    public ResponseEntity<Tv> saveNewTV(@Valid @RequestBody Tv tv) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/tv/new").toUriString());
        return ResponseEntity.created(uri).body(tvService.saveTv(tv));
    }
}
