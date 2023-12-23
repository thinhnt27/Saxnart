package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public TicketTypeEntity findById(Long id) {
        return ticketTypeRepository.findById(id).orElse(null);
    }
}
