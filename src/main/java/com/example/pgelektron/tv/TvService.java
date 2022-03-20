package com.example.pgelektron.tv;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TvService {
    private final TvRepository tvRepository;

    public TV saveTv(TV tv){
        return tvRepository.save(tv);
    }

    public TV getTvById(Long id){
        return tvRepository.getById(id);
    }
}
