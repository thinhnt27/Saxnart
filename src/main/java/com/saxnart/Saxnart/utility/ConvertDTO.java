package com.saxnart.Saxnart.utility;
import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.entity.*;
import com.saxnart.Saxnart.service.SeatService;
import com.saxnart.Saxnart.service.ShowService;
import com.saxnart.Saxnart.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class ConvertDTO {

    private static ShowService showService = null;
    private static TicketTypeService ticketTypeService = null;

    private static SeatService seatService = null;

    @Autowired
    public ConvertDTO(ShowService showService, TicketTypeService ticketTypeService, SeatService seatService) {
        this.showService = showService;
        this.ticketTypeService = ticketTypeService;
        this.seatService = seatService;
    }


        public static BookingEntity convertToBookingEntity(BookingDTO bookingDTO) {
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setName(bookingDTO.getName());
            bookingEntity.setEmail(bookingDTO.getEmail());
            bookingEntity.setTelephoneNum(bookingDTO.getTelephoneNum());
            bookingEntity.setContent(bookingDTO.getContent());

            // Convert String to Date (you might want to use LocalDate or LocalDateTime)
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            //LocalDateTime createdDate = LocalDateTime.parse(bookingDTO.getCreatedDate(), formatter);
            bookingEntity.setCreatedDate(bookingDTO.getCreatedDate());

            bookingEntity.setStatus(bookingDTO.getStatus());
            bookingEntity.setTotalPrice(bookingDTO.getTotalPrice());

            // Set the ShowEntity (assuming you have a method to get it from the database)
            ShowEntity showEntity = showService.findById(bookingDTO.getShowtimeId());
            bookingEntity.setShowtime(showEntity);

            return bookingEntity;
        }

        public static BookingDetailEntity convertToBookingDetailEntity(BookingDetailDTO bookingDetailDTO, BookingEntity bookingEntity) {
            BookingDetailEntity bookingDetailEntity = new BookingDetailEntity();
            bookingDetailEntity.setBooking(bookingEntity);
            bookingDetailEntity.setPrice(bookingDetailDTO.getPrice());
            bookingDetailEntity.setNumOfTickets(bookingDetailDTO.getNumOfTickets());

            // Set the TicketTypeEntity (assuming you have a method to get it from the database)
            TicketTypeEntity ticketTypeEntity = ticketTypeService.findById(bookingDetailDTO.getTicketTypeId());
            bookingDetailEntity.setTicketType(ticketTypeEntity);

            return bookingDetailEntity;
        }

        public static BookingSeatEntity convertToBookingSeatEntity(BookingSeatDTO bookingSeatDTO, BookingEntity bookingEntity) {
            BookingSeatEntity bookingSeatEntity = new BookingSeatEntity();
            bookingSeatEntity.setBooking(bookingEntity);

            // Set the SeatEntity (assuming you have a method to get it from the database)
            SeatEntity seatEntity = seatService.findById(bookingSeatDTO.getSeatId());
            bookingSeatEntity.setSeat(seatEntity);

            return bookingSeatEntity;
        }
    }

