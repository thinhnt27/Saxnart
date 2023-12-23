package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.entity.BookingDetailEntity;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.entity.BookingSeatEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//    public String bookSeats(BookingRequestDTO bookingDTO){
//        BookingEntity booking = new BookingEntity();
//        //booking.setCreatedDate(bookingDTO.getCreatedDate());
//        booking.setCreatedDate(new Date());
//        booking.setStatus(bookingDTO.getStatus());
//        booking.setEmail(bookingDTO.getEmail());
//        booking.setName(bookingDTO.getName());
//        booking.setContent(bookingDTO.getContent());
//        booking.setTelephoneNum(bookingDTO.getTelephoneNum());
//        int totalPrice = 0;
//        List<TicketDTO> ticketDTO = bookingDTO.getTickets();
//        for (int i = 0; i < ticketDTO.size(); i++) {
//            int ticketPrice = ticketDTO.get(i).getPrice() * ticketDTO.get(i).getNumOfTicket();
//            totalPrice += ticketPrice;
//            Optional<TicketTypeEntity> ticketType = ticketTypeRepository.findById(ticketDTO.get(i).getTicketTypeId());
//            System.out.println(ticketType.get().getName());
//            System.out.println(ticketDTO.get(i).getPrice());
//            if(ticketType.get().getWeekendPrice() != ticketDTO.get(i).getPrice() && ticketType.get().getPriceInternational() != ticketDTO.get(i).getPrice() && ticketType.get().getWeekdayPrice() != ticketDTO.get(i).getPrice()){
//                return "Price is not valid in " + ticketType.get().getName();
//            }
//            for (int j = 0; j < ticketDTO.get(i).getSeatNum().size(); j++){
//                String ticket = ticketDTO.get(i).getSeatNum().get(j);
//                SeatEntity seatNum = seatRepository.findBySeatNum(ticket);
//                if(seatNum == null){
//                    return "Do not have any seatNum!";
//                }
//            }
//        }
//        booking.setTotalPrice(totalPrice);
//        BookingEntity saveBooking = new BookingEntity();
//        for (int i = 0; i < ticketDTO.size(); i++){
//            Optional<TicketTypeEntity> ticketType = ticketTypeRepository.findById(ticketDTO.get(i).getTicketTypeId());
//            if(ticketType.isPresent()){
//                System.out.println(ticketType.get().getName());
//                    for (int j = 0; j < ticketDTO.get(i).getSeatNum().size(); j++){
//                        String ticket = ticketDTO.get(i).getSeatNum().get(j);
//                        SeatEntity seatNum = seatRepository.findBySeatNum(ticket);
//                        saveBooking = bookingRepository.save(booking);
//                        seatNum.setBooking(saveBooking);
//                        seatNum.setIsBook(true);
//                        seatRepository.save(seatNum);
//                    }
//                    BookingDetailEntity bookingDetail = new BookingDetailEntity();
//                    bookingDetail.setPrice(ticketDTO.get(i).getPrice());
//                    bookingDetail.setNumOfTickets((ticketDTO.get(i).getNumOfTicket()));
//                    bookingDetail.setTicketType(ticketType.get());
//                    bookingDetail.setBooking(saveBooking);
//                    bookingDetailRepository.save(bookingDetail);
//            }else return "The ticket is not exist!";
//        }
//        return "Hóa đơn đã thanh toán!";
//    }

//    public List<SeatEntity> getSeatsByBookingId(Long bookingId) {
//        return (List<SeatEntity>) seatRepository.findAllByBooking_Id(bookingId);
//    }
}
