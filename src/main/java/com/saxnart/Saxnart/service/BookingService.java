package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.dto.BookingDetailDTO;
import com.saxnart.Saxnart.dto.BookingSeatDTO;
import com.saxnart.Saxnart.dto.request.BookingRequestDTO;
import com.saxnart.Saxnart.dto.response.BookingDetailResponse;
import com.saxnart.Saxnart.entity.*;
import com.saxnart.Saxnart.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ShowRepository showRepository;


    public String createBooking(BookingDTO bookingDTO) {
        // Convert DTO to entities and save to the database
        List<BookingDetailDTO> bookingDetailDTOS = bookingDTO.getBookingDetails();
        double totalPrice = 0;
        for (BookingDetailDTO bookingDetailDTO : bookingDetailDTOS) {
            Optional<TicketTypeEntity> ticketType = ticketTypeRepository.findById(bookingDetailDTO.getTicketTypeId());
            if (ticketType.isPresent()) {
                if (bookingDetailDTO.getPrice() == ticketType.get().getPrice()) {
                    totalPrice += bookingDetailDTO.getPrice() * bookingDetailDTO.getNumOfTickets();
                } else return "Ticket request do no equal with ticket database";
            } else return "Do not have ticket";
        }
        if (totalPrice != bookingDTO.getTotalPrice()) return "Total price is wrong";
        BookingEntity bookingEntity = convertToBookingEntity(bookingDTO);
        List<SeatEntity> seat = seatRepository.findBookedSeatsByShowtimeId(bookingDTO.getShowtimeId());
        for (BookingSeatDTO bookingSeatDTO : bookingDTO.getBookingSeats()) {
            boolean isBooked = seat.stream()
                    .anyMatch(seats -> seats.getId().equals(bookingSeatDTO.getSeatId()));
            if (isBooked) {
                return "Seat Id do not equal bookingSeatDTO's seat id";
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
        return "Success";
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

    public List<BookingEntity> getAllBooking() {
        return bookingRepository.findAll();
    }

    public List<BookingRequestDTO> getAllBookingByShow(Long showTimeId) {
        List<BookingEntity> bookings = bookingRepository.findByStatusIsTrueAndShowtime_Id(showTimeId);
        List<BookingRequestDTO> bookingRequests = new ArrayList<>();
        for (BookingEntity booking : bookings) {
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
            for (BookingSeatEntity bookingSeat : booking.getBookingSeats()) {
                seatNum.add(bookingSeat.getSeat().getSeatNum());
            }
            bookingRequestDTO.setSeatNum(seatNum);
            bookingRequests.add(bookingRequestDTO);
        }
        return bookingRequests;
    }

    public List<String> getAllBookingSeatNumByShow(Long showTimeId) {
        List<BookingEntity> bookings = bookingRepository.findByStatusIsTrueAndShowtime_Id(showTimeId);
        List<String> seatNum = new ArrayList<>();
        for (BookingEntity booking : bookings) {
            for (BookingSeatEntity bookingSeat : booking.getBookingSeats()) {
                seatNum.add(bookingSeat.getSeat().getSeatNum());
            }
        }
        return seatNum;
    }

    public List<String> checkSeatStatusInShow(Long showTimeId, List<String> seatNum) {
        List<String> listSeatNumInShow = getAllBookingSeatNumByShow(showTimeId);
        List<String> resultList = new ArrayList<>();
        for (String listSeatNum : listSeatNumInShow) {
            seatNum.stream()
                    .filter(seat -> seat.equals(listSeatNum))
                    .forEach(resultList::add);
        }
        return resultList.isEmpty() ? null : resultList;
    }

    public String updateStatusBooking(BookingDTO booking) {
        BookingEntity bookingEntity = bookingRepository.findByEmailAndShowtime_IdAndNameAndCreatedDate(booking.getEmail(), booking.getShowtimeId(), booking.getName(), booking.getCreatedDate());
        if (bookingEntity != null) {
            bookingEntity.setStatus(false);
            bookingRepository.save(bookingEntity);
            return "Update status booking success";
        } else return "Booking does not exit";
    }

    public String paymentSuccess(BookingDTO booking) {
        BookingEntity bookingEntity = bookingRepository.findByEmailAndShowtime_IdAndNameAndCreatedDate(
                booking.getEmail(), booking.getShowtimeId(), booking.getName(), booking.getCreatedDate());
        MailConfigEntity mailConfigEntity = mailRepository.findAll().stream().findFirst().orElse(null);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (bookingEntity != null) {
            //Bỏ seat num vào mail gửi
            List<BookingSeatEntity> bookingSeatEntities = bookingSeatRepository.findByBooking_Id(bookingEntity.getId());
            StringBuilder seat = new StringBuilder();
            for (int i = 0; i < bookingSeatEntities.size(); i++) {
                seat.append(bookingSeatEntities.get(i).getSeat().getSeatNum());
                if (i < bookingSeatEntities.size() - 1) {
                    seat.append(", ");
                }
            }
            String seatString = seat.toString();
            //Format VNĐ
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,### VNĐ");
            String formatPrice = decimalFormat.format(bookingEntity.getTotalPrice());
            if (mailConfigEntity != null) {
                MimeMessage message = javaMailSender.createMimeMessage();
                try {
                    MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
                    helper.setFrom(mailConfigEntity.getUsername());
                    helper.setTo(bookingEntity.getEmail());
                    helper.setSubject("Xác nhận đặt vé thành công");
                    String htmlBody = "<html><head><meta charset=\"UTF-8\"></head><body><div style=\"text-align: center;\">"
                            + "<h2 style=\"font-weight: bold; color: black;\">Saxn'Art Club</h2>"
                            + "<div style=\"display: inline-block; background-color: #f0f0f0; padding: 20px; border-radius: 10px; text-align: left;\">"
                            + "<div style=\"border: 1px solid #000; border-radius: 10px; padding: 10px; color: black;\">"
                            + "<p>Xin chào ,</p>"
                            + "<p style=\"color: black;\">Saxn'Art xác nhận bạn đã đặt vé thành công lúc " + formatter.format(new Date())
                            + ". Bấm vào ... để xem chi tiết</p>"
                            + "<p style=\"font-weight: bold; color: black;\">Thông tin người nhận vé:</p>"
                            + "<table style=\"color: black;\">"
                            + "<tr><td>Họ và tên: </td><td>" + bookingEntity.getName() + "</td></tr>"
                            + "<tr><td>Số điện thoại: </td><td>" + bookingEntity.getTelephoneNum() + "</td></tr>"
                            + "<tr><td>Email: </td><td>" + bookingEntity.getEmail() + "</td></tr>"
                            + "</table>"
                            + "<p style=\"font-weight: bold; color: black;\">Chi tiết vé của bạn như sau:</p>"
                            + "<p style=\"color: black;\">Ghế đặt: " + seatString + "</p>"
                            + "<p style=\"color: black;\">Thời gian: " + format.format(bookingEntity.getShowtime().getShowDate()) + "</p>"
                            + "<p style=\"font-weight: bold; color: black; text-align: right;\">Tổng tiền: " + formatPrice + "</p>"
                            + "</div>"
                            + "</div></div></body></html>";
                    helper.setText(htmlBody, true);
                    javaMailSender.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            bookingEntity.setIsPayment(true);
            bookingRepository.save(bookingEntity);
            return "Update status isPayment success";

        } else return "Booking does not exit";
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
