package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public TicketTypeEntity findById(Long id) {
        return ticketTypeRepository.findById(id).orElse(null);
    }

    public List<TicketDTO> getAllTicket(){
        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
        List<TicketDTO> ticketDTOs = new ArrayList<>();

        for (TicketTypeEntity ticket : tickets) {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setId(ticket.getId());
            ticketDTO.setName(ticket.getName());
            ticketDTO.setPriceInternational(ticket.getPriceInternational());
            ticketDTO.setWeekdayPrice(ticket.getWeekdayPrice());
            ticketDTO.setWeekendPrice(ticket.getWeekendPrice());

            ticketDTOs.add(ticketDTO);
        }

        return ticketDTOs;
    }
}
