package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.request.BookingRequestDTO;
import com.saxnart.Saxnart.entity.BookingDetailEntity;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.repository.BookingDetailRepository;
import com.saxnart.Saxnart.repository.BookingRepository;
import com.saxnart.Saxnart.repository.SeatRepository;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public String bookSeats(BookingRequestDTO bookingDTO){
        BookingEntity booking = new BookingEntity();
        //booking.setCreatedDate(bookingDTO.getCreatedDate());
        booking.setCreatedDate(new Date());
        booking.setStatus(bookingDTO.getStatus());
        booking.setEmail(bookingDTO.getEmail());
        booking.setName(bookingDTO.getName());
        booking.setContent(bookingDTO.getContent());
        booking.setTelephoneNum(bookingDTO.getTelephoneNum());
        double totalPrice = 0.0;
        BookingEntity saveBooking = bookingRepository.save(booking);
        List<TicketDTO> ticketDTO = bookingDTO.getTickets();
        for (int i = 0; i < ticketDTO.size(); i++){
            Optional<TicketTypeEntity> ticketType = ticketTypeRepository.findById(ticketDTO.get(i).getTicketTypeId());
            if(ticketType.isPresent()){
                if(ticketType.get().getWeekdayPrice() != ticketDTO.get(i).getPrice() || ticketType.get().getWeekendPrice() != ticketDTO.get(i).getPrice() || ticketType.get().getPriceInternational() != ticketDTO.get(i).getPrice()){
                    return "The price is not valid in " + ticketType.get().getName();
                }
                double ticketPrice = ticketDTO.get(i).getPrice() * ticketDTO.get(i).getNumOfTicket();
                totalPrice += ticketPrice;
                for (int j = 0; j < ticketDTO.get(i).getSeatNum().size(); j++){
                    String ticket = ticketDTO.get(i).getSeatNum().get(j);
                    SeatEntity seatNum = seatRepository.findBySeatNum(ticket);
                    if(seatNum == null){
                        return "Do not have any seatNum!";
                    }
                    seatNum.setBooking(saveBooking);
                    seatNum.setIsBook(true);
                    seatRepository.save(seatNum);
                }
                BookingDetailEntity bookingDetail = new BookingDetailEntity();
                bookingDetail.setPrice(ticketDTO.get(i).getPrice());
                bookingDetail.setNumOfTickets((ticketDTO.get(i).getNumOfTicket()));
                bookingDetail.setTicketType(ticketType.get());
                bookingDetail.setBooking(saveBooking);
                bookingDetailRepository.save(bookingDetail);
            }else return "The ticket is not exist!";
        }
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);
        return "Hóa đơn đã thanh toán!";
    }

    public List<SeatEntity> getSeatsByBookingId(Long bookingId) {
        return (List<SeatEntity>) seatRepository.findAllByBooking_Id(bookingId);
    }
}
