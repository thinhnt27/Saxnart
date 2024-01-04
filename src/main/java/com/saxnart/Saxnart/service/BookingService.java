package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.dto.request.BookingRequestDTO;
import com.saxnart.Saxnart.dto.response.BookingDetailResponse;
import com.saxnart.Saxnart.entity.BookingDetailEntity;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.entity.BookingSeatEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<BookingRequestDTO> getAllBookingByShow(Long showTimeId){
        List<BookingEntity> bookings = bookingRepository.findByStatusIsTrueAndShowtime_Id(showTimeId);
        List<BookingRequestDTO> bookingRequests =new ArrayList<>();
        for (BookingEntity booking: bookings) {
            BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
            bookingRequestDTO.setId(booking.getId());
            bookingRequestDTO.setName(booking.getName());
            bookingRequestDTO.setEmail(booking.getEmail());
            bookingRequestDTO.setTelephoneNum(booking.getTelephoneNum());
            bookingRequestDTO.setContent(booking.getContent());
            bookingRequestDTO.setCreatedDate(booking.getCreatedDate());
            bookingRequestDTO.setStatus(booking.getStatus());
            bookingRequestDTO.setTotalPrice(bookingRequestDTO.getTotalPrice());
            List<String> seatNum = new ArrayList<>();
            for (BookingSeatEntity bookingSeat: booking.getBookingSeats()) {
                seatNum.add(bookingSeat.getSeat().getSeatNum());
            }
            bookingRequestDTO.setSeatNum(seatNum);
            bookingRequests.add(bookingRequestDTO);
        }
        return bookingRequests;
    }

    public List<String> getAllBookingSeatNumByShow(Long showTimeId){
        List<BookingEntity> bookings = bookingRepository.findByStatusIsTrueAndShowtime_Id(showTimeId);
        List<String> seatNum = new ArrayList<>();
        for (BookingEntity booking: bookings) {
            for (BookingSeatEntity bookingSeat: booking.getBookingSeats()) {
                seatNum.add(bookingSeat.getSeat().getSeatNum());
            }
        }
        return seatNum;
    }

    public List<String> checkSeatStatusInShow(Long showTimeId, List<String> seatNum){
        List<String> listSeatNumInShow =  getAllBookingSeatNumByShow(showTimeId);
        List<String> resultList = new ArrayList<>();
        for (String listSeatNum: listSeatNumInShow) {
            seatNum.stream()
                    .filter(seat -> seat.equals(listSeatNum))
                    .forEach(resultList::add);
        }
        return resultList.isEmpty() ? null : resultList;
    }
    public String updateStatusBooking(BookingDTO booking){
        BookingEntity bookingEntity = bookingRepository.findByEmailAndShowtime_IdAndNameAndCreatedDate(booking.getEmail(),booking.getShowtimeId(), booking.getName(), booking.getCreatedDate());
        if(bookingEntity != null){
            bookingEntity.setStatus(false);
            bookingRepository.save(bookingEntity);
            return "Update status booking success";
        }else  return "Booking does not exit";
    }
    public String checkBookingOver15minute(BookingDTO booking) throws ParseException {
        BookingEntity bookingEntity = bookingRepository.findByEmailAndShowtime_IdAndNameAndCreatedDate(
                booking.getEmail(), booking.getShowtimeId(), booking.getName(), booking.getCreatedDate());

        if (bookingEntity != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Date bookingDateTime = bookingEntity.getCreatedDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            String bookingDateTimeString = dateFormat.format(bookingDateTime);
            Date parsedBookingDateTime = dateFormat.parse(bookingDateTimeString);
            Date modifiedBookingDateTime = Date.from(parsedBookingDateTime.toInstant().plus(Duration.ofMinutes(15)));
            if (modifiedBookingDateTime.before(currentDate)) {
                bookingEntity.setStatus(false);
                bookingRepository.save(bookingEntity);
                return "Booking is over payment session";
            } else {
                return "Payment session is still valid";
            }
        } else {
            return "Booking does not exist";
        }
    }



}
