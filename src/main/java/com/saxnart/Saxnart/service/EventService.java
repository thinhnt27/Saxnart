package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.EventEntity;
import com.saxnart.Saxnart.entity.GalleryEntity;
import com.saxnart.Saxnart.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventEntity> getAllEvent() {
        return eventRepository.findAll();
    }

    public EventEntity getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public EventEntity saveEvent(EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    public String updateStatus(Long id) {
        EventEntity event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            event.setStatus(false);
            eventRepository.save(event);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }

    public String deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return "Xóa thành công";
        } else {
            return "Không tìm thấy bản ghi để xóa";
        }
    }
}
