package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public ShowEntity findById(Long id) {
        return showRepository.findById(id).orElse(null);
    }
}
