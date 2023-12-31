package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public ShowEntity findById(Long id) {
        return showRepository.findById(id).orElse(null);
    }
    public List<ShowEntity> getAllShow() {
        List<ShowEntity> shows = showRepository.findAll();
        return shows;
    }

    public List<ShowEntity> getShowHaveSpecialIsTrue(){
        return showRepository.findByIsSpecialIsTrue();
    }

    public List<ShowEntity> getShowHaveSpecialIsFalse(){
        return showRepository.findByIsSpecialIsFalse();
    }

    public void creatShow(ShowEntity showEntity){
        showRepository.save(showEntity);
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsTrue(String currentDate) {
        return showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsFalse(String currentDate) {
        return showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
    }
}
