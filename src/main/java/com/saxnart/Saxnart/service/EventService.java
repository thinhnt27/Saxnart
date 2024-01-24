package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.BlogEntity;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.EventEntity;
import com.saxnart.Saxnart.entity.GalleryEntity;
import com.saxnart.Saxnart.extention.ChuyenNgheSixException;
import com.saxnart.Saxnart.extention.EventException;
import com.saxnart.Saxnart.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            event.setStatus(!event.getStatus());
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

    public EventEntity update(Long Id, EventEntity eventEntity){
        Optional<EventEntity> event = eventRepository.findById(Id);
        if (event.isPresent()){
            EventEntity entity = this.getEventById(Id);
            entity.setTitle(eventEntity.getTitle());
            entity.setImage(eventEntity.getImage());
            entity.setContent(eventEntity.getContent());
            entity.setStatus(eventEntity.getStatus());
            eventRepository.save(entity);
            return entity;
        }
        throw new EventException("updated failed");
    }
}
