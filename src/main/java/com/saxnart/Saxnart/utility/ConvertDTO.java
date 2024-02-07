package com.saxnart.Saxnart.utility;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.*;
import com.saxnart.Saxnart.service.SeatService;
import com.saxnart.Saxnart.service.ShowService;
import com.saxnart.Saxnart.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        bookingEntity.setIsPayment(bookingDTO.getIsPayment());
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

//    public static List<ShowDTO> convertShowsToDTOs(List<ShowEntity> shows, List<TicketTypeEntity> tickets) {
//        return shows.stream()
//                .map(show -> convertShowToDTO(show, tickets))
//                .toList();
//    }

//    private static ShowDTO convertShowToDTO(ShowEntity show, List<TicketTypeEntity> tickets) {
//        List<TicketDTO> ticketDTOs = new ArrayList<>();
//        for (TicketTypeEntity ticket : tickets) {
//            TicketDTO ticketDTO = new TicketDTO();
//            if (isWeekend(show.getShowDate()) && !show.getIsSpecial()) {
//                ticketDTO.setId(ticket.getId());
//                ticketDTO.setName(ticket.getName());
//                ticketDTO.setWeekendPrice(ticket.getWeekendPrice());
//            } else if (!isWeekend(show.getShowDate()) && !show.getIsSpecial()) {
//                ticketDTO.setId(ticket.getId());
//                ticketDTO.setName(ticket.getName());
//                ticketDTO.setWeekdayPrice(ticket.getWeekdayPrice());
//            } else {
//                ticketDTO.setId(ticket.getId());
//                ticketDTO.setName(ticket.getName());
//                ticketDTO.setPriceInternational(ticket.getPriceInternational());
//            }
//            ticketDTOs.add(ticketDTO);
//        }
//
//        return new ShowDTO(
//                show.getId(),
//                show.getTitle(),
//                show.getShowDate(),
//                show.getPicture(),
//                show.getAuthor(),
//                show.getIsSpecial(),
//                show.getContent(),
//                show.getPicture(),
//                ticketDTOs
//        );
//    }
//
//    private static boolean isWeekend(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        return dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
//    }
    public static ShowEntity convertToShowEntity(ShowDTO showDTO) {
        ShowEntity showEntity = new ShowEntity();
        showEntity.setId(showDTO.getId());
        showEntity.setTitle(showDTO.getTitle());
        showEntity.setShowDate(showDTO.getShowDate());
        showEntity.setPicture(showDTO.getPicture());
        showEntity.setAuthor(showDTO.getAuthor());
        showEntity.setIsSpecial(showDTO.getIsSpecial());
        showEntity.setContent(showDTO.getContent());

        // Convert TicketDTO list to TicketTypeEntity set
        if (showDTO.getTicketType() != null) {
            Set<TicketTypeEntity> ticketTypeEntities = showDTO.getTicketType().stream()
                    .map(ConvertDTO::convertToTicketTypeEntity)
                    .collect(Collectors.toSet());
            // Set the TicketTypeEntity set to the ShowEntity
            showEntity.setTicketShows(ticketTypeEntities);
        }

        return showEntity;
    }
    public static TicketTypeEntity convertToTicketTypeEntity(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        }
        TicketTypeEntity ticketTypeEntity = new TicketTypeEntity();
        ticketTypeEntity.setName(ticketDTO.getName());
        ticketTypeEntity.setPrice(ticketDTO.getPrice());
        // Set other properties if needed

        return ticketTypeEntity;
    }

    public static ShowDTO convertToShowDTO(ShowEntity showEntity) {
        List<TicketTypeEntity> ticketTypeEntities = ticketTypeService.findByShowtimeId(showEntity.getId());
        ShowDTO showDTO = new ShowDTO();
        showDTO.setId(showEntity.getId());
        showDTO.setTitle(showEntity.getTitle());
        showDTO.setShowDate(showEntity.getShowDate());
        showDTO.setPicture(showEntity.getPicture());
        showDTO.setAuthor(showEntity.getAuthor());
        showDTO.setIsSpecial(showEntity.getIsSpecial());
        showDTO.setContent(showEntity.getContent());
        // Map other fields accordingly
        showDTO.setTicketType(
                ticketTypeService.findByShowtimeId(showEntity.getId()).stream()
                        .map(ConvertDTO::convertToTicketDTO)
                        .collect(Collectors.toList())
        );
        return showDTO;
    }

    public static List<ShowDTO> convertToShowDTOList(List<ShowEntity> showEntities) {
        return showEntities.stream()
                .map(ConvertDTO::convertToShowDTO)
                .collect(Collectors.toList());
    }

    private static TicketDTO convertToTicketDTO(TicketTypeEntity ticketTypeEntity) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticketTypeEntity.getId());
        ticketDTO.setName(ticketTypeEntity.getName());
        ticketDTO.setPrice(ticketTypeEntity.getPrice());
        // Map other fields accordingly
        return ticketDTO;
    }
}

