package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.extention.TickerTypeException;
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
    public TicketTypeEntity getTicketTypeById(Long id) {
        return ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TickerTypeException("TicketType not found with id: " + id));
    }

    public TicketTypeEntity createTicketType(TicketTypeEntity ticketType) {
        return ticketTypeRepository.save(ticketType);
    }

    public TicketTypeEntity updateTicketType(Long id, TicketTypeEntity updatedTicketType) {
        TicketTypeEntity existingTicketType = getTicketTypeById(id);
        // Perform update logic here
        existingTicketType.setName(updatedTicketType.getName());
        existingTicketType.setPriceInternational(updatedTicketType.getPriceInternational());
        existingTicketType.setWeekdayPrice(updatedTicketType.getWeekdayPrice());
        existingTicketType.setWeekendPrice(updatedTicketType.getWeekendPrice());
        // Update other fields as needed

        return ticketTypeRepository.save(existingTicketType);
    }

    public String deleteTicketType(Long id) {
        if (ticketTypeRepository.existsById(id)) {
            ticketTypeRepository.deleteById(id);
            return "Xóa thành công";
        } else {
            return "Không tìm thấy bản ghi để xóa";
        }
    }
}

