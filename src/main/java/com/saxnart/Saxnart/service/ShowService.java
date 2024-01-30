package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.ShowRepository;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
import com.saxnart.Saxnart.utility.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public ShowEntity findById(Long id) {
        return showRepository.findById(id).orElse(null);
    }
    public List<ShowEntity> getAllShow() {
        List<ShowEntity> shows = showRepository.findAll();
        return shows;
    }

    public List<ShowDTO> getAllShowDTO(){
        List<ShowEntity> shows = showRepository.findAll();
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }
    public List<ShowEntity> getShowHaveSpecialIsTrue(){
        return showRepository.findByIsSpecialIsTrue();
    }
    public List<ShowDTO> getShowDTOHaveSpecialIsTrue(){
        List<ShowEntity> shows = showRepository.findByIsSpecialIsTrue();
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }


    public List<ShowEntity> getShowHaveSpecialIsFalse(){
        return showRepository.findByIsSpecialIsFalse();
    }

    public List<ShowDTO> getShowDTOHaveSpecialIsFalse(){
        List<ShowEntity> shows = showRepository.findByIsSpecialIsFalse();
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }

    public void creatShow(ShowEntity showEntity){
        showRepository.save(showEntity);
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsTrue(Date currentDate) {
        return showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
    }

    public List<ShowDTO> findShowsDTOAfterDateAndSpecialIsTrue(Date currentDate) {
        List<ShowEntity> shows = showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsFalse(Date currentDate) {
        return showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
    }

    public List<ShowDTO> findShowsDTOAfterDateAndSpecialIsFalse(Date currentDate) {
        List<ShowEntity> shows = showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }

    public List<ShowEntity> findShowsAfterDate(Date currentDate) {
        return showRepository.findShowsAfterCurrentDate(currentDate);
    }

    public List<ShowDTO> findShowsDTOAfterDate(Date currentDate) {
        List<ShowEntity> shows = showRepository.findShowsAfterCurrentDate(currentDate);
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
        return showDTOs;
    }
}
