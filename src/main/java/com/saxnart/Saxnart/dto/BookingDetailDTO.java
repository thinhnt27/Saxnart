package com.saxnart.Saxnart.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailDTO {
    private Long bookingId;
    private Long ticketTypeId;
    private int price;
    private int numOfTickets;
}
