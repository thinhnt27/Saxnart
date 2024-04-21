package com.saxnart.Saxnart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private String name;
    private String email;
    private String telephoneNum;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;
    private Boolean status;
    private Boolean isPayment;
    private double totalPrice;
    private List<BookingSeatDTO> bookingSeats;
    private List<BookingDetailDTO> bookingDetails;
    private Long showtimeId;
    private String ip;
}
