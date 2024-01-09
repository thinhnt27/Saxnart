package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.BookingEventEntity;
import com.saxnart.Saxnart.entity.ContactEntity;
import com.saxnart.Saxnart.repository.BookingEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookingEventService {

    @Autowired
    private BookingEventRepository bookingEventRepository;
    public List<BookingEventEntity> getAllBookingEvents(){
        return bookingEventRepository.findAll();
    }

    public BookingEventEntity getBookingEventById(Long id) {
        return bookingEventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking Event not found with id: " + id));
    }

    public BookingEventEntity saveBookingEvent(BookingEventEntity bookingEvent) {
        return bookingEventRepository.save(bookingEvent);
    }

    public BookingEventEntity updateBookingEvent(Long bookingEventId){
        Optional<BookingEventEntity> contactOptional = bookingEventRepository.findById(bookingEventId);
        if (contactOptional.isPresent()) {
            BookingEventEntity bookingEvent = contactOptional.get();
            bookingEvent.setStatus(true);
            bookingEventRepository.save(bookingEvent);
            return bookingEvent;
        }else return contactOptional.get();
    }
}
