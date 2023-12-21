package com.saxnart.Saxnart.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private List<String> seatNum;

    private Long ticketTypeId;

    private int numOfTicket;

    private int price;
}
