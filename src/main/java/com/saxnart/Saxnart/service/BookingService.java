package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.dto.response.BookingDetailResponse;
import com.saxnart.Saxnart.entity.BookingDetailEntity;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.entity.BookingSeatEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.saxnart.Saxnart.utility.ConvertDTO.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;



    public Boolean createBooking(BookingDTO bookingDTO) {
        // Convert DTO to entities and save to the database
        BookingEntity bookingEntity = convertToBookingEntity(bookingDTO);
        List<SeatEntity> seat = seatRepository.findBookedSeatsByShowtimeId(bookingDTO.getShowtimeId());
        for (BookingSeatDTO bookingSeatDTO : bookingDTO.getBookingSeats()) {
            boolean isBooked = seat.stream()
                    .anyMatch(seats -> seats.getId().equals(bookingSeatDTO.getSeatId()));
            if(isBooked){
                return false;
            }
        }
        bookingRepository.save(bookingEntity);
        for (BookingDetailDTO bookingDetailDTO : bookingDTO.getBookingDetails()) {
            BookingDetailEntity bookingDetailEntity = convertToBookingDetailEntity(bookingDetailDTO, bookingEntity);
            bookingDetailRepository.save(bookingDetailEntity);
        }
        for (BookingSeatDTO bookingSeatDTO : bookingDTO.getBookingSeats()) {
            BookingSeatEntity bookingSeatEntity = convertToBookingSeatEntity(bookingSeatDTO, bookingEntity);
            bookingSeatRepository.save(bookingSeatEntity);
        }
        return true;
    }
    public List<BookingDetailResponse> getBookingDetailsByShowTime(Long showTimeId) {
        List<BookingEntity> bookings = bookingRepository.findByStatusIsTrueAndShowtime_Id(showTimeId);

        List<BookingDetailResponse> bookingDetailsResponses = new ArrayList<>();
        for (BookingEntity booking : bookings) {
            BookingDetailResponse bookingDetailsResponse = new BookingDetailResponse();
            bookingDetailsResponse.setBookingId(booking.getId());
            List<BookingDetailEntity> bookingDetail = bookingDetailRepository.findAllByBookingId(booking.getId());
            bookingDetailsResponse.setBookingDetailEntities(bookingDetail);
            bookingDetailsResponses.add(bookingDetailsResponse);
        }
        return bookingDetailsResponses;
    }

    public List<BookingEntity> getAllBooking(){
        return bookingRepository.findAll();
    }

    public List<BookingEntity> getAllBookingByShow(Long showTimeId){
        return bookingRepository.findByShowtime_Id(showTimeId);
    }


}
