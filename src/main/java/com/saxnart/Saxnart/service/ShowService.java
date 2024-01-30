package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.ShowRepository;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
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
    public List<ShowEntity> getShowHaveSpecialIsTrue(){
        return showRepository.findByIsSpecialIsTrue();
    }
    public List<ShowDTO> getShowDTOHaveSpecialIsTrue(){
        List<ShowEntity> shows = showRepository.findByIsSpecialIsTrue();
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<ShowDTO> showDTOs = shows.stream()
                .map(show -> {
                    List<TicketDTO> ticketDTOS = new ArrayList<>();
                    for (TicketTypeEntity ticket: tickets) {
                        TicketDTO ticketTypeDTO = new TicketDTO();
                        if(isWeekend(show.getShowDate()) && !show.getIsSpecial()){
                            ticketTypeDTO.setId(ticket.getId());
                            ticketTypeDTO.setName(ticket.getName());
                            ticketTypeDTO.setWeekendPrice(ticket.getWeekendPrice());
                        }else if(!isWeekend(show.getShowDate()) && !show.getIsSpecial()){
                            ticketTypeDTO.setId(ticket.getId());
                            ticketTypeDTO.setName(ticket.getName());
                            ticketTypeDTO.setWeekdayPrice(ticket.getWeekdayPrice());
                        }else {
                            ticketTypeDTO.setId(ticket.getId());
                            ticketTypeDTO.setName(ticket.getName());
                            ticketTypeDTO.setPriceInternational(ticket.getPriceInternational());
                        }
                        ticketDTOS.add(ticketTypeDTO);
                    }
                    return new ShowDTO(
                            show.getId(),
                            show.getTitle(),
                            show.getShowDate(),
                            show.getPicture(),
                            show.getAuthor(),
                            show.getIsSpecial(),
                            show.getContent(),
                            show.getImage(),
                            ticketDTOS
                    );
                })
                .toList();
        return showDTOs;
    }

    private boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public List<ShowEntity> getShowHaveSpecialIsFalse(){
        return showRepository.findByIsSpecialIsFalse();
    }

    public void creatShow(ShowEntity showEntity){
        showRepository.save(showEntity);
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsTrue(Date currentDate) {
        return showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsFalse(Date currentDate) {
        return showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
    }

    public List<ShowEntity> findShowsAfterDate(Date currentDate) {
        return showRepository.findShowsAfterCurrentDate(currentDate);
    }
}
